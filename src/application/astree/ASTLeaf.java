package application.astree;

import java.util.ArrayList;
import java.util.Iterator;
import application.Token;

public class ASTLeaf extends ASNode {
    private static ArrayList<ASNode> empty = new ArrayList<ASNode>();
    protected Token token;

    public ASTLeaf(Token t){
        token = t;
    }

    @Override
    public ASNode child(int i){
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int numOfChildren(){
        return 0;
    }

    @Override
    public Iterator<ASNode> children() {
        return empty.iterator();
    }

    @Override
    public String location() {
        return "Line: " + token.getLineNumber();
    }

    public String toString(){
        return token.getText();
    }

    public Token getToken(){
        return token;
    }
}
