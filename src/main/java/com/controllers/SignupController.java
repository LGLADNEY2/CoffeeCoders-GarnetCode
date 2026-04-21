package com.controllers;

import java.io.IOException;

import com.techprep.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Controller for the signup view. Handles navigation from the signup screen.
 * @author Coffee Coders
 */
public class SignupController {

    /**
     * Navigate to the home view.
     *
     * @param event UI action event
     * @throws IOException when the FXML cannot be loaded
     */
    @FXML
    void goToHome(ActionEvent event) throws IOException {
        App.setRoot("home");
    }

    /**
     * Navigate to the login view.
     *
     * @param event UI action event
     * @throws IOException when the FXML cannot be loaded
     */
    @FXML
    void goToLogin(ActionEvent event) throws IOException {
        App.setRoot("login");
    }
}
