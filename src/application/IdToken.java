package application;

import java.util.ArrayList;

public class IdToken extends Token{
    private String text;
    private static ArrayList<String> keywords = new ArrayList<String>(){{
        add("while");
        add("if");
        add("else");
        add("print");
        add("def");
        add("class");
        add("extends");
    }};
    private static ArrayList<String> operators = new ArrayList<String>(){{
        add("+");
        add("-");
        add("*");
        add("/");
        add("%");
        add("=");
        add("+=");
        add("-=");
        add("*=");
        add("/=");
        add("%=");
        add("==");
        add("!=");
        add("<");
        add(">");
        add("<=");
        add(">=");
        add("!");
        add("&&");
        add("||");
        add("{");
        add("}");
        add("(");
        add(")");
        add("[");
        add("]");
        add("++");
        add("--");
        add(";");
        add(".");
        add(",");
        add("\"");
    }};
    protected IdToken(int line, String id) {
        super(line);
        text = id;
    }
    public boolean isIdentifier() { return true; }
    public boolean isKeyword() {
        return keywords.indexOf(text) != -1;
    }
    public boolean isOperator() {
        return operators.indexOf(text) != -1;
    }
    public boolean isEOL() {
        return text.equals(EOL);
    }
    public int getType() { return 2; }
    public String getText() { return text; }
}
