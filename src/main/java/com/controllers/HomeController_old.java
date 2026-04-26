package com.controllers;

import com.techprep.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.Objects;

/**
 * Legacy controller for the home view; replaced by HomeController.
 * @author Coffee Coders
 */
public class HomeController_old {

    @FXML
    private void goToLogin(ActionEvent event) throws IOException{
        App.setRoot("login"); 
    }

    @FXML
    private void goToLoginFromNav(MouseEvent event) throws IOException {
        App.setRoot("login");
    }

}