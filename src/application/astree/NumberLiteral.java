package application.astree;
import application.Token;


public class NumberLiteral extends ASTLeaf {
    public NumberLiteral(Token t){
        super(t);
    }

    public double value(){
        return token.getNumber();
    }
}
