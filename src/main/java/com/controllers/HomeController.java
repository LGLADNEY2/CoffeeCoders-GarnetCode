package com.controllers;

import java.io.IOException;
import java.util.Objects;

import com.techprep.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.shape.*;

public class HomeController {

    @FXML
    private void goToLogin(ActionEvent event) throws IOException{
        App.setRoot("login"); 
    }

    @FXML
    private void goToLoginFromNav(MouseEvent event) throws IOException {
        App.setRoot("login");
    }

    /*
        This code initializes the image in the StackPane, and provides the logic that lets it scale and crop when the window is resized.
        I tried to get it working in both the FXML and the stylesheet, but neither did what I wanted it to.
        Code was written by Jack, with help from ChatGPT (I got frustrated).
     */
    @FXML
    private StackPane main;
    private Image backgroundImage;

    // Initializes background image, sets initial size, listeners call updateBackground when the window is resized
    @FXML
    public void initialize() {
        backgroundImage = new Image(
                Objects.requireNonNull(
                        getClass().getResource("/com/techprep/images/uofsccampusvillage.jpg")
                ).toExternalForm()
    );
        updateBackground();
        main.widthProperty().addListener((obs, oldValue, newValue) -> updateBackground());
        main.heightProperty().addListener((obs, oldValue, newValue) -> updateBackground());
    }

    // Makes size parameter, Makes background image based on the size, sets background to bgImage
    private void updateBackground() {
        BackgroundSize size = new BackgroundSize(
                100,100, true, true, false, true
        );
        BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size);
        main.setBackground(new Background(bgImage));
    }

}