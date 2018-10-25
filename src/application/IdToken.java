package application;

public class IdToken extends Token{
    private String text;
    protected IdToken(int line, String id) {
        super(line);
        text = id;
    }
    public boolean isIdentifier() { return true; }
    public int getType() { return 2; }
    public String getText() { return text; }
}
