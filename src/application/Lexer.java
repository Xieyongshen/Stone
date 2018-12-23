package application;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    public static String regexPat = "\\s*((//.*)|([0-9]+(\\.[0-9]+)?)|(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")"
            + "|([A-Z_a-z][A-Z_a-z0-9]*)|\\+=|-=|\\*=|/=|%=|\\+\\+|--|==|<=|>=|&&|\\|\\||(\\p{Punct}))?";
    public static String regexChina = "[\\u4e00-\\u9fa5\\u3002\\uff1b\\uff0c\\uff1a\\u201c\\u201d\\uff08\\uff09\\u3001\\uff1f\\u300a\\u300b]";
    public static String regexChinaStr = "\"[\\u4e00-\\u9fa5\\u3002\\uff1b\\uff0c\\uff1a\\u201c\\u201d\\uff08\\uff09\\u3001\\uff1f\\u300a\\u300b]*\"";

    private Pattern pattern = Pattern.compile(regexPat);
    private Pattern patternChina = Pattern.compile(regexChina);
    private Pattern patternChinaStr = Pattern.compile(regexChinaStr);


    private ArrayList<Token> queue = new ArrayList<Token>();
    private boolean hasMore;
    private String input;
    private LineNumberReader reader;
    private int start = 0;

    public Lexer(String s) {
        hasMore = true;
        input = s;
        reader = new LineNumberReader(new StringReader(input));
    }

    private void initializeLexer(){
        hasMore = true;
        start = 0;
        reader = new LineNumberReader(new StringReader(input));
    }

    public Token read()  throws ParseException {
        if (fillQueue(0))
            return queue.remove(0);
        else{
            initializeLexer();
            return Token.EOF;
        }
    }

    public Token peek(int i)  throws ParseException {
        if (fillQueue(i))
            return queue.get(i);
        else{
            initializeLexer();
            return Token.EOF;
        }
    }

    private boolean fillQueue(int i)  throws ParseException {
        while (i >= queue.size()) {
            if (hasMore)
				readLine();
			else
                return false;
        }
        return true;
    }

    protected void readLine()  throws ParseException  {
        String line;
        boolean correct = true;

        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new ParseException(e);
        }


        if (line == null) {
            hasMore = false;
            return;
        }

        start = input.indexOf(line, start);
        int length = line.length();

        int lineNo = reader.getLineNumber();

        Matcher matcher = pattern.matcher(line);
        matcher.useTransparentBounds(true).useAnchoringBounds(false);
        int pos = 0;
        int endPos = line.length();
        while (pos < endPos) {
            matcher.region(pos, endPos);
            if (matcher.lookingAt() && matcher.end() != pos) {
            	if(matcher.group(8) != null && KwToken.operators.indexOf(matcher.group(1)) == -1){
                    System.out.println("Wrong identifier at line " + lineNo + ", posiotion " + matcher.end());
                    correct = false;
                    break;
                }
                addToken(lineNo, matcher);
                pos = matcher.end();
            } else {
                System.out.println("Wrong identifier at line " + lineNo + ", posiotion " + matcher.end());
                correct = false;
                break;
            }
        }

        if(correct){
            queue.add(new KwToken(lineNo, Token.EOL));
            start = start + length;
        }else{
            queue.clear();
            StringBuilder sb = new StringBuilder(input);
            sb.replace(start, start + length + 1, "");
            input = sb.toString();
        }

    }

    protected void addToken(int lineNo, Matcher matcher) {
        String m = matcher.group(1);
        if (m != null)
            if (matcher.group(2) == null) {
                Token token;
                if (matcher.group(3) != null)
                    token = new NumToken(lineNo, Double.parseDouble(m));
                else if (matcher.group(5) != null)
                    token = new StrToken(lineNo, toStringLiteral(m));
                else if (matcher.group(7) != null){
                    if(KwToken.keywords.indexOf(m) != -1){
                        token = new KwToken(lineNo, m);
                    }else{
                        token = new IdToken(lineNo, m);
                    }
                }
                else
                    token = new KwToken(lineNo, m);
                queue.add(token);
            }
    }

    protected String toStringLiteral(String s) {
        StringBuilder sb = new StringBuilder();
        int len = s.length() - 1;
        for (int i = 1; i < len; i++) {
            char c = s.charAt(i);
            if (c == '\\' && i + 1 < len) {
                int c2 = s.charAt(i + 1);
                if (c2 == '"' || c2 == '\\')
                    c = s.charAt(++i);
                else if (c2 == 'n') {
                    i++;
                    c = '\n';
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

}
