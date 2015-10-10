package net.toddobryan.java.image;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Transform;

public class Stack extends Image {
	private Image front;
	private Image back;
	
	private Bounds newBounds;
	private Transform backTransform;
	private Transform frontTransform;
	
	public Stack(Image front, Image back, XAlign xAlign, YAlign yAlign, double dx, double dy) {
		this.front = front;
		this.back = back;
		
		BoundingBox backBounds = Stack.translateRect(
				back.getDisplayBounds(), 
				Stack.getDx(back.getDisplayBounds(), xAlign, 0),
				Stack.getDy(back.getDisplayBounds(), yAlign, 0));
		BoundingBox frontBounds = Stack.translateRect(
				front.getDisplayBounds(), 
				Stack.getDx(front.getDisplayBounds(), xAlign, dx),
				Stack.getDy(front.getDisplayBounds(), yAlign, dy));
		double newLeft = Math.min(backBounds.getMinX(), frontBounds.getMinX());
		// TODO: why is this not getMaxX() for both?
		double newRight = Math.max(backBounds.getMinX() + backBounds.getWidth(), frontBounds.getMinX() + frontBounds.getWidth());
		double newTop = Math.min(backBounds.getMinY(), frontBounds.getMinY());
		double newBottom = Math.max(backBounds.getMaxY(), frontBounds.getMaxY());
		this.newBounds = new BoundingBox(newLeft, newTop, newRight - newLeft, newBottom - newTop);
		this.backTransform = Transform.translate(backBounds.getMinX() - newLeft,  backBounds.getMinY() - newTop);
		this.frontTransform = Transform.translate(frontBounds.getMinX() - newLeft, frontBounds.getMinY() - newTop);
	}
	
	public Stack(Image front, Image back) {
		this(front, back, XAlign.CENTER, YAlign.CENTER, 0.0, 0.0);
	}
	
	public Stack(Image front, Image back, Align align) {
		this(front, back, align.xAlign, align.yAlign, 0.0, 0.0);
	}
	
	public Shape getBounds() {
		return new Rectangle(newBounds.getMinX(), newBounds.getMinY(), newBounds.getWidth(), newBounds.getHeight());
	}
	
	private Node newNode() {
		Node backView = new Group(back.buildImage());
		backView.getTransforms().add(backTransform);
		Node frontView = new Group(front.buildImage());
		frontView.getTransforms().add(frontTransform);
		Pane pane = new Pane();
		pane.getChildren().add(new Group(backView, frontView));
		pane.setPrefWidth(newBounds.getWidth());
		pane.setPrefHeight(newBounds.getHeight());
		return pane;
	}
	
	public Node buildImage() {
		if (Platform.isFxApplicationThread()) {
			return newNode();
		} else {
			Task<Node> theNode = new Task<Node>() {
    			protected Node call() {
    				return Stack.this.newNode();
    			}
    		};
    		try {
    			Platform.runLater(theNode);
    			return theNode.get();
    		} catch (Exception e) {
    			throw new RuntimeException(e);
    		}
		}
	}

	private static BoundingBox translateRect(BoundingBox rect, double dx, double dy) {
		return new BoundingBox(rect.getMinX() + dx, rect.getMinY(), rect.getWidth(), rect.getHeight());
	}
	
	private static double getDx(BoundingBox bds, XAlign xAlign, double xOffset) {
		double amtToAdd;
		if (xAlign == XAlign.LEFT) {
			amtToAdd = 0;
		} else if (xAlign == XAlign.CENTER) {
			amtToAdd = -0.5 * bds.getWidth();
		} else { // (xAlign == XAlign.RIGHT)
			amtToAdd = -1.0 * bds.getMaxX();
		}
		return xOffset + amtToAdd;
    }
	
	private static double getDy(BoundingBox bds, YAlign yAlign, double yOffset) {
		double amtToAdd;
		if (yAlign == YAlign.TOP) {
			amtToAdd = 0;
		} else if (yAlign == YAlign.CENTER) {
			amtToAdd = -0.5 * bds.getHeight();
		} else { // (yAlign == YAlign.Bottom)
			amtToAdd = -1.0 * bds.getMaxY();
		}
		return yOffset + amtToAdd;
	}
	
	
}
