package application.astree;

import java.util.ArrayList;
import java.util.Iterator;

public class ASTList extends ASNode {
    private ArrayList<ASNode> children;

    public ASTList(ArrayList<ASNode> list){
        children = list;
    }

    @Override
    public ASNode child(int i){
        return children.get(i);
    }

    @Override
    public int numOfChildren(){
        return children.size();
    }

    @Override
    public Iterator<ASNode> children() {
        return children.iterator();
    }

    @Override
    public String location() {
        for(ASNode node : children){
            String s = node.location();
            if(s != null){
                return s;
            }
        }
        return null;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        String sp = "";
        for(ASNode node : children){
            sb.append(sp);
            sp = " ";
            sb.append(node.toString());
        }
        return sb.append(")").toString();
    }
}
