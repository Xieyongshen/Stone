package application;

public class StrToken extends Token{
    private String literal;
    protected StrToken(int line, String str) {
        super(line);
        literal = str;
    }
    public boolean isString() { return true; }
    public int getType() { return 3; }
    public String getText() { return literal; }
}
