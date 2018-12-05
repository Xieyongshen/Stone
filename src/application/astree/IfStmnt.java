package application.astree;
import java.util.ArrayList;
import java.util.List;

public class IfStmnt extends ASTList {
    public IfStmnt(ArrayList<ASNode> c) { super(c); }
    public ASNode condition() { return child(0); }
    public ASNode thenBlock() { return child(1); }
    public ASNode elseBlock() {
        return numOfChildren() > 2 ? child(2) : null;
    }
    public String toString() {
        return "(if " + condition() + " " + thenBlock()
                 + " else " + elseBlock() + ")";
    }
}
