package org.dupontmanual.java.image;

import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import scala.xml.NodeSeq;
import scala.xml.XML;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class Image {
    public abstract Node buildImage();
    
    private void showDialog() {
    	final Stage dialogStage = new Stage(StageStyle.UTILITY);
    	dialogStage.setTitle("Image");
    	VBox root = new VBox();
    	root.setAlignment(Pos.TOP_CENTER);
    	Button closeButton = new Button();
    	closeButton.setText("OK");
    	closeButton.setOnAction(new EventHandler<ActionEvent>() {
    		public void handle(ActionEvent evt) {
    			dialogStage.close();
    		}
    	});
    	root.getChildren().addAll(this.buildImage(), closeButton);
    	dialogStage.setScene(new Scene(root));
    	dialogStage.showAndWait();
    }
    
    public void display() {
    	if (Platform.isFxApplicationThread()) {
    		showDialog();
    	} else {
    		Runnable dialogShower = new Runnable() {
    			public void run() {
    				showDialog();
    			}
    		};
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
	
	public NodeSeq inlineImgTag() {
		return XML.loadString("<img src=\"data:image/png;base64," + base64png() + "\" />");
	}
}
