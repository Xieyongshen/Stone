package application.astree;
import java.util.ArrayList;
import java.util.List;

public class ClassStmnt extends ASTList {
    public ClassStmnt(ArrayList<ASNode> c) { super(c); }
    public String name() { return ((ASTLeaf)child(0)).getToken().getText(); }
    public String superClass() {
        if (numOfChildren() < 3)
            return null;
        else
            return ((ASTLeaf)child(1)).getToken().getText();
    }
    public ClassBody body() { return (ClassBody)child(numOfChildren() - 1); }
    public String toString() {
        String parent = superClass();
        if (parent == null)
            parent = "*";
        return "(class " + name() + " " + parent + " " + body() + ")";
    }
}
