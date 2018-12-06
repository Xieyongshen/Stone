package application.astree;
import java.util.ArrayList;

public class DefStmnt extends ASTList {
    public DefStmnt(ArrayList<ASNode> c) { super(c); }
    public String name() { return ((ASTLeaf)child(0)).getToken().getText(); }
    public ParameterList parameters() { return (ParameterList)child(1); }
    public BlockStmnt body() { return (BlockStmnt)child(2); } 
    public String toString() {
        return "(def " + name() + " " + parameters() + " " + body() + ")";
    }
}
