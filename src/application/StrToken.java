package application;

public class StrToken extends Token{
    private String literal;
    protected StrToken(int line, String str) {
        super(line);
        literal = str;
    }
    public int getType() { return 2; }
    public String getText() { return literal; }
}
