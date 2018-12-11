package application.astree;
import java.util.ArrayList;


public class ArrayRef extends Postfix {
    public ArrayRef(ArrayList<ASNode> c) { super(c); }
    public ASNode index() { return child(0); }
    public String toString() { return "[" + index() + "]"; }
}
