package application.astree;
import java.util.ArrayList;

public class Arguments extends Postfix {
    public Arguments(ArrayList<ASNode> c) { super(c); }
    public int size() { return numOfChildren(); }
}
