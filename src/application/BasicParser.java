package application;
import static application.Parser.rule;
import java.util.HashSet;
import application.Parser.Operators;
import application.astree.*;

public class BasicParser {
    HashSet<String> reserved = new HashSet<String>();
    Operators operators = new Operators();
    Operators logOperators = new Operators();
    Parser expr0 = rule();
    Parser primary = rule(PrimaryExpr.class)
        .or(rule().sep("(").ast(expr0).sep(")"),
            rule().number(NumberLiteral.class),
            rule().identifier(Name.class, reserved),
            rule().string(StringLiteral.class));
    Parser factor = rule().or(rule(NegativeExpr.class).sep("-").ast(primary), primary);
    Parser expr = expr0.expression(BinaryExp.class, factor, operators);
    Parser condition = rule().expression(ConditionExp.class, expr, logOperators);
    Parser statement0 = rule();
    Parser block = rule(BlockStmnt.class)
        .sep("{").option(statement0)
        .repeat(rule().sep(";", Token.EOL).option(statement0))
        .sep("}");
    Parser simple = rule(PrimaryExpr.class).ast(expr);
    Parser statement = statement0.or(
            rule(IfStmnt.class).sep("if").ast(condition).ast(block)
                    .repeat(rule(IfStmnt.class).sep("elseif").ast(condition).ast(block))
                    .option(rule().sep("else").ast(block)),
            rule(WhileStmnt.class).sep("while").ast(condition).ast(block),
            simple);
    Parser program = rule().or(statement, rule(NullStmnt.class))
                           .sep(";", Token.EOL);

    public BasicParser() {
        reserved.add(";");
        reserved.add("}");
        reserved.add(Token.EOL);

        operators.add("=", 1, Operators.RIGHT);
        operators.add("+=", 1, Operators.RIGHT);
        operators.add("-=", 1, Operators.RIGHT);
        operators.add("*=", 1, Operators.RIGHT);
        operators.add("/=", 1, Operators.RIGHT);
        operators.add("%=", 1, Operators.RIGHT);
        operators.add("==", 2, Operators.LEFT);
        operators.add("!=", 2, Operators.LEFT);
        operators.add(">", 2, Operators.LEFT);
        operators.add(">=", 2, Operators.LEFT);
        operators.add("<", 2, Operators.LEFT);
        operators.add("<=", 2, Operators.LEFT);
        operators.add("+", 3, Operators.LEFT);
        operators.add("-", 3, Operators.LEFT);
        operators.add("*", 4, Operators.LEFT);
        operators.add("/", 4, Operators.LEFT);
        operators.add("%", 4, Operators.LEFT);
        logOperators.add("&&", 1, Operators.LEFT);
        logOperators.add("||", 1, Operators.LEFT);
    }
    public ASNode parse(Lexer lexer) throws ParseException {
        return program.parse(lexer);
    }
}
