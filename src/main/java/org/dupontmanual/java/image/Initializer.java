package org.dupontmanual.java.image;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Initializer extends Application {
	public void start(Stage stage) {
        stage.setTitle("My JavaFX Application");
        Label label = new Label("Leave this window open until\n you're ready to quit.");
        stage.setScene(new Scene(new Group(label), 200, 50));
        stage.show();
        Image.fxIsInitialized = true;
    }
}

