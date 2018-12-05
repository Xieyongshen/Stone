package application.astree;
import java.util.ArrayList;

public class NegativeExpr extends ASTList {
    public NegativeExpr(ArrayList<ASNode> c) { super(c); }
    public ASNode operand() { return child(0); }
    public String toString() {
        return "-" + operand();
    }
}
