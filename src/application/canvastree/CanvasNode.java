package application.canvastree;


import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class CanvasNode implements Iterable<CanvasNode> {
    Circle circle;
    Text text;
    ArrayList<Line> lines;

    public abstract CanvasNode child(int i);
    public abstract int numOfChildren();
    public abstract Iterator<CanvasNode> children();
    public abstract void addIntoPane(Pane pn);
    public abstract void addChild(CanvasNode node);
    public Iterator<CanvasNode> iterator(){
        return children();
    }

    CanvasNode(String s){
        circle = new Circle(40);
        circle.setFill(Color.LIGHTBLUE);
        text = new Text(s);
        text.setFill(Color.RED);
        text.setFont(Font.font(20));
    }

    public void setX(double x) {
        circle.setCenterX(x);
        text.setX(x - text.getLayoutBounds().getWidth() / 2);
    }

    public void setY(double y) {
        circle.setCenterY(y);
        text.setY(y + text.getLayoutBounds().getHeight() / 4);
    }

    public double getX() {
        return circle.getCenterX();
    }

    public double getY() {
        return circle.getCenterY();
    }

    public String toString(){ return text.getText(); }

}
