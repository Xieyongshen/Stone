package application.astree;
import java.util.ArrayList;

public class ParameterList extends ASTList {
    public ParameterList(ArrayList<ASNode> c) { super(c); }
    public String name(int i) { return ((ASTLeaf)child(i)).getToken().getText(); }
    public int size() { return numOfChildren(); }
}
