package application;

import java.util.ArrayList;

public class BinaryExp extends ASTList {
    public BinaryExp(ArrayList<ASNode> list){
        super(list);
    }

    public ASNode left(){
        return child(0);
    }

    public String operator(){
        return ((ASTLeaf) child(1)).getToken().toString();
    }

    public ASNode right(){
        return child(2);
    }
}
