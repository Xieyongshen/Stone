package application;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import application.astree.*;

public class Parser {
    protected static abstract class Element {
        protected abstract void parse(Lexer lexer, ArrayList<ASNode> res)
            throws ParseException;
        protected abstract boolean match(Lexer lexer) throws ParseException;
    }

    protected static class Tree extends Element {
        protected Parser parser;
        protected Tree(Parser p) { parser = p; }
        protected void parse(Lexer lexer, ArrayList<ASNode> res)
            throws ParseException
        {
            res.add(parser.parse(lexer));
        }
        protected boolean match(Lexer lexer) throws ParseException { 
            return parser.match(lexer);
        }
    }

    protected static class OrTree extends Element {
        protected Parser[] parsers;
        protected OrTree(Parser[] p) { parsers = p; }
        protected void parse(Lexer lexer, ArrayList<ASNode> res)
            throws ParseException
        {
            Parser p = choose(lexer);
            if (p == null)
                throw new ParseException(lexer.peek(0));
            else
                res.add(p.parse(lexer));
        }
        protected boolean match(Lexer lexer) throws ParseException {
            return choose(lexer) != null;
        }
        protected Parser choose(Lexer lexer) throws ParseException {
            for (Parser p: parsers)
                if (p.match(lexer))
                    return p;

            return null;
        }
        protected void insert(Parser p) {
            Parser[] newParsers = new Parser[parsers.length + 1];
            newParsers[0] = p;
            System.arraycopy(parsers, 0, newParsers, 1, parsers.length);
            parsers = newParsers;
        }
    }

    protected static class Repeat extends Element {
        protected Parser parser;
        protected boolean onlyOnce;
        protected Repeat(Parser p, boolean once) { parser = p; onlyOnce = once; }
        protected void parse(Lexer lexer, ArrayList<ASNode> res)
            throws ParseException
        {
            while (parser.match(lexer)) {
            	ASNode t = parser.parse(lexer);
                if (t.getClass() != ASTList.class || t.numOfChildren() > 0)
                    res.add(t);
                if (onlyOnce)
                    break;
            }
        }
        protected boolean match(Lexer lexer) throws ParseException {
            return parser.match(lexer);
        }
    }

    protected static abstract class AToken extends Element {
        protected Factory factory;
        protected AToken(Class<? extends ASTLeaf> type) {
            if (type == null)
                type = ASTLeaf.class;
            factory = Factory.get(type, Token.class);
        }
        protected void parse(Lexer lexer, ArrayList<ASNode> res)
            throws ParseException
        {
            Token t = lexer.read();
            if (test(t)) {
            	ASNode leaf = factory.make(t);
                res.add(leaf);
            }
            else
                throw new ParseException(t);
        }
        protected boolean match(Lexer lexer) throws ParseException {
            return test(lexer.peek(0));
        }
        protected abstract boolean test(Token t); 
    }

    protected static class IdToken extends AToken {
        HashSet<String> reserved;
        protected IdToken(Class<? extends ASTLeaf> type, HashSet<String> r) {
            super(type);
            reserved = r != null ? r : new HashSet<String>();
        }
        protected boolean test(Token t) {
            return (t.getType()==3||t.getType()==4||t.getType()==5) && !reserved.contains(t.getText());
        }
    }

    protected static class NumToken extends AToken {
        protected NumToken(Class<? extends ASTLeaf> type) { super(type); }
        protected boolean test(Token t) { return t.getType()==1; }
    }

    protected static class StrToken extends AToken {
        protected StrToken(Class<? extends ASTLeaf> type) { super(type); }
        protected boolean test(Token t) { return t.getType()==2; }
    }

    protected static class Leaf extends Element {
        protected String[] tokens;
        protected Leaf(String[] pat) { tokens = pat; }
        protected void parse(Lexer lexer, ArrayList<ASNode> res)
            throws ParseException
        {
            Token t = lexer.read();

            if (t.getType()==3||t.getType()==4||t.getType()==5||t.isEOL()) 
            	for (String token: tokens) 
                	 if (token.equals(t.getText())) {
                         find(res, t);
                         return;
                     }
                            
            if (tokens.length > 0)
                throw new ParseException(tokens[0] + " expected.", t);
            else
                throw new ParseException(t);
        }
        protected void find(ArrayList<ASNode> res, Token t) {
            res.add(new ASTLeaf(t));
        }
        protected boolean match(Lexer lexer) throws ParseException {
            Token t = lexer.peek(0);
            if (t.getType()==3||t.getType()==4||t.getType()==5||t.isEOL())
                for (String token: tokens)
                    if (token.equals(t.getText()))
                        return true;

            return false;
        }
    }

    protected static class Skip extends Leaf {
        protected Skip(String[] t) { super(t); }
        protected void find(ArrayList<ASNode> res, Token t) {}
    }

    public static class Precedence {
        int value;
        boolean leftAssoc; // left associative
        public Precedence(int v, boolean a) {
            value = v; leftAssoc = a;
        }
    }

    public static class Operators extends HashMap<String,Precedence> {
        public static boolean LEFT = true;
        public static boolean RIGHT = false;
        public void add(String name, int prec, boolean leftAssoc) {
            put(name, new Precedence(prec, leftAssoc));
        }
    }

    protected static class Expr extends Element {
        protected Factory factory;
        protected Operators ops;
        protected Parser factor;
        protected Expr(Class<? extends ASNode> clazz, Parser exp,
                       Operators map)
        {
            factory = Factory.getForASTList(clazz);
            ops = map;
            factor = exp;
        }
        public void parse(Lexer lexer, ArrayList<ASNode> res) throws ParseException {
            ASNode right = factor.parse(lexer);
            Precedence prec;
            while ((prec = nextOperator(lexer)) != null)
                right = doShift(lexer, right, prec.value);

            res.add(right);
        }
        private ASNode doShift(Lexer lexer, ASNode left, int prec)
            throws ParseException
        {
            ArrayList<ASNode> list = new ArrayList<ASNode>();
            list.add(left);
            list.add(new ASTLeaf(lexer.read()));
            ASNode right = factor.parse(lexer);
            Precedence next;
            while ((next = nextOperator(lexer)) != null
                   && rightIsExpr(prec, next))
                right = doShift(lexer, right, next.value);

            list.add(right);
            return factory.make(list);
        }
        private Precedence nextOperator(Lexer lexer) throws ParseException {
            Token t = lexer.peek(0);
            if (t.getType()==3||t.getType()==4||t.getType()==5||t.isEOL())
                return ops.get(t.getText());
            else
                return null;
        }
        private static boolean rightIsExpr(int prec, Precedence nextPrec) {
            if (nextPrec.leftAssoc)
                return prec < nextPrec.value;
            else
                return prec <= nextPrec.value;
        }
        protected boolean match(Lexer lexer) throws ParseException {
            return factor.match(lexer);
        }
    }

    public static final String factoryName = "create";
    protected static abstract class Factory {
        protected abstract ASNode make0(Object arg) throws Exception;
        protected ASNode make(Object arg) {
            try {
                return make0(arg);
            } catch (IllegalArgumentException e1) {
                throw e1;
            } catch (Exception e2) {
                throw new RuntimeException(e2); // this compiler is broken.
            }
        }
        protected static Factory getForASTList(Class<? extends ASNode> clazz) {
            Factory f = get(clazz, ArrayList.class);
            if (f == null)
                f = new Factory() {
                    protected ASNode make0(Object arg) throws Exception {
                        ArrayList<ASNode> results = (ArrayList<ASNode>)arg;
                        if (results.size() == 1)
                            return results.get(0);
                        else
                            return new ASTList(results);
                    }
                };
            return f;
        }
        protected static Factory get(Class<? extends ASNode> clazz,
                                     Class<?> argType)
        {
            if (clazz == null)
                return null;
            try {
                final Method m = clazz.getMethod(factoryName,
                                                 new Class<?>[] { argType });
                return new Factory() {
                    protected ASNode make0(Object arg) throws Exception {
                        return (ASNode)m.invoke(null, arg);
                    }
                };
            } catch (NoSuchMethodException e) {}
            try {
                final Constructor<? extends ASNode> c
                    = clazz.getConstructor(argType);
                return new Factory() {
                    protected ASNode make0(Object arg) throws Exception {
                        return c.newInstance(arg);
                    }
                };
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected ArrayList<Element> elements;
    protected Factory factory;

    public Parser(Class<? extends ASNode> clazz) {
        reset(clazz);
    }
    protected Parser(Parser p) {
        elements = p.elements;
        factory = p.factory;
    }
    public ASNode parse(Lexer lexer) throws ParseException {
        ArrayList<ASNode> results = new ArrayList<ASNode>();
        for (Element e: elements)
            e.parse(lexer, results);

        return factory.make(results);
    }
    protected boolean match(Lexer lexer) throws ParseException {
        if (elements.size() == 0)
            return true;
        else {
            Element e = elements.get(0);
            return e.match(lexer);
        }
    }
    public static Parser rule() { return rule(null); }
    public static Parser rule(Class<? extends ASNode> clazz) {
        return new Parser(clazz);
    }
    public Parser reset() {
        elements = new ArrayList<Element>();
        return this;
    }
    public Parser reset(Class<? extends ASNode> clazz) {
        elements = new ArrayList<Element>();
        factory = Factory.getForASTList(clazz);
        return this;
    }
    public Parser number() {
        return number(null);
    }
    public Parser number(Class<? extends ASTLeaf> clazz) {
        elements.add(new NumToken(clazz));
        return this;
    }
    public Parser identifier(HashSet<String> reserved) {
        return identifier(null, reserved);
    }
    public Parser identifier(Class<? extends ASTLeaf> clazz,
                             HashSet<String> reserved)
    {
        elements.add(new IdToken(clazz, reserved));
        return this;
    }
    public Parser string() {
        return string(null);
    }
    public Parser string(Class<? extends ASTLeaf> clazz) {
        elements.add(new StrToken(clazz));
        return this;
    }
    public Parser token(String... pat) {
        elements.add(new Leaf(pat));
        return this;
    }
    public Parser sep(String... pat) {
        elements.add(new Skip(pat));
        return this;
    }
    public Parser ast(Parser p) {
        elements.add(new Tree(p));
        return this;
    }
    public Parser or(Parser... p) {
        elements.add(new OrTree(p));
        return this;
    }
    public Parser maybe(Parser p) {
        Parser p2 = new Parser(p);
        p2.reset();
        elements.add(new OrTree(new Parser[] { p, p2 }));
        return this;
    }
    public Parser option(Parser p) {
        elements.add(new Repeat(p, true));
        return this;
    }
    public Parser repeat(Parser p) {
        elements.add(new Repeat(p, false));
        return this;
    }
    public Parser expression(Parser subexp, Operators operators) {
        elements.add(new Expr(null, subexp, operators));
        return this;
    }
    public Parser expression(Class<? extends ASNode> clazz, Parser subexp,
                             Operators operators) {
        elements.add(new Expr(clazz, subexp, operators));
        return this;
    }
    public Parser insertChoice(Parser p) {
        Element e = elements.get(0);
        if (e instanceof OrTree)
            ((OrTree)e).insert(p);
        else {
            Parser otherwise = new Parser(this);
            reset(null);
            or(p, otherwise);
        }
        return this;
    }
}
