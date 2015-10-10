package net.toddobryan.java.image;

import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.jdom2.Element;

import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class Image {
	protected static boolean fxIsInitialized = false;
	
    public abstract Node buildImage();

    public static void initialize() {
    	new Thread(new Runnable() {
    		public void run() {
    		    Application.launch(Initializer.class);
    		}
    	}).start();
    	while (!Image.fxIsInitialized) {
    		try {
    			Thread.sleep(1000);
    		} catch (InterruptedException e) {
    			throw new RuntimeException(e);
    		}
    	}
    }
    
    private void showDialog() {
    	System.out.println("Creating dialogStage...");
    	final Stage dialogStage = new Stage(StageStyle.UTILITY);
    	dialogStage.setTitle("Image");
    	VBox root = new VBox();
    	root.setAlignment(Pos.TOP_CENTER);
    	System.out.println("Creating button...");
    	Button closeButton = new Button();
    	closeButton.setText("OK");
    	closeButton.setOnAction(new EventHandler<ActionEvent>() {
    		public void handle(ActionEvent evt) {
    			dialogStage.close();
    		}
    	});
    	System.out.println("Adding children...");
    	root.getChildren().addAll(this.buildImage(), closeButton);
    	dialogStage.setScene(new Scene(root));
    	System.out.println("Showing and waiting...");
    	dialogStage.show();
    	System.out.println("Done showing and waiting...");
    }
    
    public void display() {
    	if (Platform.isFxApplicationThread()) {
    		System.out.println("Running from FX Application Thread...");
    		showDialog();
    	} else {
    		Runnable dialogShower = new Runnable() {
    			public void run() {
    				showDialog();
    			}
    		};
    		System.out.println("Running later...");
    		Platform.runLater(dialogShower);
    	}
    }
    
    private WritableImage _writableImg = null;
    
    private WritableImage createImgScene() {
    	Scene scene = new Scene(new Group(this.buildImage()));
    	return scene.snapshot(null);
    }
    
    public WritableImage writableImg() {
    	if (_writableImg == null) {
    		Task<WritableImage> wrImg = new Task<WritableImage>() {
    			protected WritableImage call() {
    				return Image.this.createImgScene();
    			}
    		};
    		if (Platform.isFxApplicationThread()) {
    			_writableImg = createImgScene();
    		} else {
    			try {
    			    Platform.runLater(wrImg);
    			    _writableImg = wrImg.get();
    			} catch (Exception e) {
    				throw new RuntimeException(e);
    			}
    		}
    	}
    	return _writableImg;
    }
    
    private RenderedImage _savableImg = null;
    
    public RenderedImage savableImg() {
    	if (_savableImg == null) {
    		_savableImg = SwingFXUtils.fromFXImage(writableImg(), null);
    	}
    	return _savableImg;
    }
    
    public void saveAsDisplayed(String filename) {
    	String filenameWithExt = filename.endsWith(".png") ? filename : filename + ".png";
    	try {
    		ImageIO.write(savableImg(), "png", new File(filenameWithExt));
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	}
    }
    
	public byte[] bytesPng() {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		try {
			ImageIO.write(savableImg(), "png", bytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	    return bytes.toByteArray();
	}
	
	public String base64png() {
		return Base64.encodeBase64String(bytesPng());
	}
	
	public Element inlineImgTag() {
		Element elem = new Element("img");
		elem.setAttribute("src", "data:image/png;base64," + base64png());
		return elem;
	}
	
	public abstract Shape getBounds();
	
	public BoundingBox getDisplayBounds() {
		Bounds bds = this.getBounds().getBoundsInParent();
		return new BoundingBox(bds.getMinX(), bds.getMinY(), bds.getWidth(), bds.getHeight());
	}
	
	public Double getWidth() {
		return this.getDisplayBounds().getWidth();
	}
	
	public Double getHeight() {
		return this.getDisplayBounds().getHeight();
	}
	
	public Image stackOn(Image img) {
		return new Stack(this, img, Align.CENTER);
	}
	
	public Image stackOn(Image img, Align align) {
		return new Stack(this, img, align);
	}	
}