package net.toddobryan.java.image;

import java.io.File;
import java.net.URL;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Bitmap extends Image {
	private javafx.scene.image.Image bitmap;
	private String name;
	
	public Node buildImage() {
		return new ImageView(this.bitmap);
	}
	
	public Shape getBounds() {
		return new Rectangle(0, 0, this.bitmap.getWidth(), this.bitmap.getHeight());
	}
	
	public String toString() {
		return String.format("Bitmap: %s", name);
	}
	
	private Bitmap(File file, String name) {
		try {
			this.bitmap = new javafx.scene.image.Image(file.toURI().toURL().toString());
			this.name = name;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private Bitmap(URL url, String name) {
		this.bitmap = new javafx.scene.image.Image(url.toString());
		this.name = name;
	}
	
	public static Image fromFile(String path, String name) {
		return new Bitmap(new File(path), name);
	}
	
	public static Image fromFile(String path) {
		return fromFile(path, null);
	}
	
	public static Image fromUrl(String url, String name) {
		try {
			return new Bitmap(new URL(url), name);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Image fromUrl(String url) {
		return fromUrl(url, null);
	}
	
	private static Image fromWorkspace(String path, String name) {
		return new Bitmap(path.getClass().getResource(path), name);
	}
	
	/** a 33x31 pixel picture of a calendar */
	public static final Image CALENDAR = Bitmap.fromWorkspace("/calendar.png", "Calendar");

	/** a 48x48 pixel picture of a frustrated computer operator */
	public static final Image HACKER= Bitmap.fromWorkspace("/mad_hacker.png", "Hacker");

	/** a 45x68 pixel picture of a tablet with hieroglyphics */
	public static final Image GLYPHS = Bitmap.fromWorkspace("/hieroglyphics.png", "Glyphs");

	/** a 67x39 pixel picture of book with a large question mark over it */
	public static final Image BOOK = Bitmap.fromWorkspace("/qbook.png", "Book");

	/** a 38x39 pixel picture of a very blocky person */
	public static final Image STICK_PERSON = Bitmap.fromWorkspace("/stick-figure.png", "StickPerson");

	/** an 85x44 pixel picture of a train car */
	public static final Image TRAIN_CAR = Bitmap.fromWorkspace("/train_car.png", "TrainCar");

	/** a 129x44 pixel picture of an old-fashioned train engine with a coal car */
	public static final Image TRAIN_ENGINE = Bitmap.fromWorkspace("/train_engine.png", "TrainEngine");
	
}
