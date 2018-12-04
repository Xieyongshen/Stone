package application;

public class IdToken extends Token {
    private String text;
    protected IdToken(int line, String id) {
        super(line);
        text = id;
    }

    @Override
    public int getType() { return 3; }

    @Override
    public String getText() { return text; }
}
