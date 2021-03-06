package application;
import static application.Parser.rule;
import application.astree.Fun;

public class ClosureParser extends ArrayParser {
    public ClosureParser() {
        primary.insertChoice(rule(Fun.class)
                                 .sep("fun").ast(paramList).ast(block));
    }
}
