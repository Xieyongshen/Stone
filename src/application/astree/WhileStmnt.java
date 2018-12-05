package application.astree;
import java.util.ArrayList;

public class WhileStmnt extends ASTList {
    public WhileStmnt(ArrayList<ASNode> c) { super(c); }
    public ASNode condition() { return child(0); }
    public ASNode body() { return child(1); }
    public String toString() {
        return "(while " + condition() + " " + body() + ")";
    }
}
