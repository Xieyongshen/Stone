package application.astree;
import application.Token;


public class Name extends ASTLeaf {
    public Name(Token t){
        super(t);
    }

    public String name(){
        return token.getText();
    }
}
