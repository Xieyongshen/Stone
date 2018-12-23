package application.canvastree;

import application.astree.ASNode;
import application.astree.ASTLeaf;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class CanvasTree {
	
	private CanvasNode root;
	private double paddingLR;
	private double paddingTB;
	private double canvasWidth;
	private double canvasHeight;
	private Iterator<ASNode> tree;
	
	public CanvasTree(ASNode node) {
        if(node.numOfChildren() == 0)
            root = new CanvasLeaf("Program");
        else{
            root = new CanvasList("Program");
        }
		paddingLR = 40;
		paddingTB = 0;
		canvasWidth = getWidth(root, node, paddingLR) + paddingLR * 2;
		canvasHeight = getHeight(root, 1) + paddingTB * 2;
	}
	
	public CanvasTree(ASNode node, double lr, double tb) {
        if(node.numOfChildren() == 0)
            root = new CanvasLeaf("Program");
        else{
            root = new CanvasList("Program");
        }
		paddingLR = lr;
		paddingTB = tb;
		canvasWidth = getWidth(root, node, paddingLR) + paddingLR * 2;
		canvasHeight = getHeight(root, 1) + paddingTB * 2;
	}
	
	private double getWidth(CanvasNode cvNode, ASNode node, double start) {
		double cwidth = 0.0;
        double x = 0.0;
		if(node.numOfChildren() != 0){
            Iterator iterNode = node.children();
		    while(iterNode.hasNext()){
                ASNode child = (ASNode) iterNode.next();
                CanvasNode cvchild;
                if(child.numOfChildren() == 0){
                    cvchild = new CanvasLeaf(child.toString());
                }else{
                    String name = child.getClass().getName();
                    String[] className = name.split("\\.");
                    name = className[className.length - 1];
                    cvchild = new CanvasList(name);
                }
                cvNode.addChild(cvchild);
                cwidth += getWidth(cvchild, child, start + cwidth);
            }
            Iterator iterCNode = cvNode.children();
            while(iterCNode.hasNext()){
                CanvasNode cnode = (CanvasNode) iterCNode.next();
                x += cnode.getX();
            }
            x /= cvNode.numOfChildren();
        }else{
		    x = start + 50;
        }
        System.out.println(cvNode.toString() + " pos: " + x);
		cvNode.setX(x);
		return cwidth >= 100 ? cwidth : 100;
	}
	
	private double getHeight(CanvasNode cvNode, int depth) {
		double cheight = 0.0, height = 0.0;
		double y = 0.0;
        if(cvNode.numOfChildren() != 0){
            Iterator iterCNode = cvNode.children();
            while(iterCNode.hasNext()){
                CanvasNode cnode = (CanvasNode) iterCNode.next();
                cheight = getHeight(cnode, depth + 1);
                if(cheight >= height) height = cheight;
            }
        }else{
            height = depth * 100;
        }
		cvNode.setY(paddingTB + depth * 100 - 50);
		return height;
	}
	
	public void draw() {
		try {
            Stage st = new Stage();
            Pane pn = new Pane();
            ScrollPane spn = new ScrollPane();
    		pn.setPrefWidth(canvasWidth);
    		pn.setPrefHeight(canvasHeight);
    		root.addIntoPane(pn);
    		spn.setContent(pn);
            st.setTitle("Canvas");
            st.setScene(new Scene(spn, 800, 600));
            st.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
