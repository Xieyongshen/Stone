package application.astree;
import java.util.ArrayList;

public class ArrayLiteral extends ASTList {
    public ArrayLiteral(ArrayList<ASNode> list) { super(list); }
    public int size() { return numOfChildren(); }
}
