package application;

import java.util.Iterator;

public abstract class ASNode implements Iterable<ASNode> {
    public abstract ASNode child(int i);
    public abstract int numOfChildren();
    public abstract Iterator<ASNode> children();
    public abstract String location();
    public Iterator<ASNode> iterator(){
        return children();
    }
}
