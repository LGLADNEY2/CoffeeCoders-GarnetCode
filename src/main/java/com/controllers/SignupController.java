package com.controllers;

import java.io.IOException;

import com.techprep.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SignupController {

    @FXML
    void goToHome(ActionEvent event) throws IOException {
        App.setRoot("home");
    }

    @FXML
    void goToLogin(ActionEvent event) throws IOException {
        App.setRoot("login");
    }
}
