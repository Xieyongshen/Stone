package application;

public class IdToken extends Token {
    private String text;
    protected IdToken(int line, String id) {
        super(line);
        text = id;
    }
    public int getType() { return 3; }
    public String getText() { return text; }
}
