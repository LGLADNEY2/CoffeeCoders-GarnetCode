package com.controllers;

import java.io.IOException;

import com.techprep.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

/**
 * Controller for the home view; provides navigation to login.
 * @author Coffee Coders
 */
public class HomeController {

    /**
     * Navigate to the login view from the main UI.
     *
     * @param event UI action event
     * @throws IOException when the FXML cannot be loaded
     */
    @FXML
    private void goToLogin(ActionEvent event) throws IOException{
        App.setRoot("login"); 
    }

    /**
     * Navigate to login via the navigation element (mouse event).
     *
     * @param event mouse event from the nav control
     * @throws IOException when the FXML cannot be loaded
     */
    @FXML
    private void goToLoginFromNav(MouseEvent event) throws IOException {
        App.setRoot("login");
    }

}