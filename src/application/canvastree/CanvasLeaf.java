package application.canvastree;

import application.Token;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Iterator;

public class CanvasLeaf extends CanvasNode{
    private static ArrayList<CanvasNode> empty = new ArrayList<CanvasNode>();
    protected Token token;

    public CanvasLeaf(String s){
        super(s);
        lines = new ArrayList<Line>();
    }

    @Override
    public CanvasNode child(int i){
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int numOfChildren(){
        return 0;
    }

    @Override
    public Iterator<CanvasNode> children() {
        return empty.iterator();
    }

    public void addIntoPane(Pane pn) {
        pn.getChildren().add(circle);
        pn.getChildren().add(text);
    }

    public void addChild(CanvasNode node){
        System.out.println("Illegal operation");
    }
}
