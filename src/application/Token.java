package application;

public abstract class Token {
	
	public static final Token EOF = new Token(-1) {};
	public static final String EOL = "\\n";
	private int lineNumber;

	public Token(int line) {
		lineNumber = line;
	}
	
	public int getLineNumber() {
		return lineNumber;
	}

	//璇嗗埆token绫诲瀷锛�0--鏈煡锛�1--NumToken锛�2--StrToken, 3--IdToken, 4--Keyword, 5--Operator
	public int getType() { return 0; }

	public boolean isEOL() { return false; }
	
	public double getNumber() {
		throw new RuntimeException("not number token");
	}
	
	public String getText() {
		return "";
	}

}
