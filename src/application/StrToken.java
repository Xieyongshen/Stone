package application;

public class StrToken extends Token{
    private String literal;
    protected StrToken(int line, String str) {
        super(line);
        literal = str;
    }

    @Override
    public int getType() { return 2; }

    @Override
    public String getText() { return literal; }
}
