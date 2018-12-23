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

public class CanvasList extends CanvasNode {
    private ArrayList<CanvasNode> children = new ArrayList<CanvasNode>();
    protected Token token;

    public CanvasList(String s){
        super(s);
        lines = new ArrayList<Line>();
    }

    @Override
    public CanvasNode child(int i){
        return children.get(i);
    }

    @Override
    public int numOfChildren(){
        return children.size();
    }

    @Override
    public Iterator<CanvasNode> children() {
        return children.iterator();
    }

    public void addChild(CanvasNode node){
        children.add(node);
    }

    public void addIntoPane(Pane pn) {
        for(CanvasNode node : children){
            Line line = new Line(getX(), getY(), node.getX(), node.getY());
            line.setStroke(Color.LIGHTSALMON);
            line.setStrokeWidth(3);
            pn.getChildren().add(line);
            node.addIntoPane(pn);
        }
        pn.getChildren().add(circle);
        pn.getChildren().add(text);
    }
}
