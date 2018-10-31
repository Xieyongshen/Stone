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

	public int getType() { return 0; }

	public boolean isIdentifier() {
		return false;
	}
	
	public boolean isNumber() {
		return false;
	}
	
	public boolean isString() {
		return false;
	}

	public boolean isKeyword() { return false; }

	public boolean isOperator() { return false; }

	public boolean isEOL() { return false; }
	
	public double getNumber() {
		throw new RuntimeException("not number token");
	}
	
	public String getText() {
		return "";
	}
}
