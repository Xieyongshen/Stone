package application.astree;
import java.util.ArrayList;

public class Dot extends Postfix {
    public Dot(ArrayList<ASNode> c) { super(c); }
    public String name() { return ((ASTLeaf)child(0)).getToken().getText(); }
    public String toString() { return "." + name(); } 
}
