package application;

import java.util.ArrayList;

public class KwToken extends Token{
    private String text;
    public static ArrayList<String> keywords = new ArrayList<String>(){{
        add("while");
        add("if");
        add("else");
        add("print");
        add("def");
        add("class");
        add("extends");
    }};
    public static ArrayList<String> operators = new ArrayList<String>(){{
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
    protected KwToken(int line, String keyword) {
        super(line);
        text = keyword;
    }
    public boolean isEOL() {
        return text.equals(EOL);
    }
    public int getType() {
        if(keywords.indexOf(text) != -1){
            return 4;
        }else if(operators.indexOf(text) != -1){
            return 5;
        }else{
            return 0;
        }
    }
    public String getText() { return text; }
}
