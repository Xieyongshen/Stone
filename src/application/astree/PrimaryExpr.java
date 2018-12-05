package application.astree;
import java.util.ArrayList;

public class PrimaryExpr extends ASTList {
    public PrimaryExpr(ArrayList<ASNode> c) { super(c); }
    public static ASNode create(ArrayList<ASNode> c) {
        return c.size() == 1 ? c.get(0) : new PrimaryExpr(c);
    }
}
