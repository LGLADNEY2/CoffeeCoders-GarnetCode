package com.techprep;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App entry point and simple FXML loader utility.
 * @author Coffee Coders
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("home"));
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    /**
     * Sets the current scene root to the given FXML resource.
     *
     * @param fxml name of the fxml file (without extension)
     * @throws IOException if the fxml cannot be loaded
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Loads an FXML resource and returns the root node.
     *
     * @param fxml name of the fxml file (without extension)
     * @return loaded Parent root node
     * @throws IOException if the resource cannot be loaded
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}