package application.astree;
import java.util.ArrayList;

public class NonExpr extends ASTList {
    public NonExpr(ArrayList<ASNode> c) { super(c); }
    public ASNode operand() { return child(0); }
    public String toString() {
        return "!" + operand();
    }
}
