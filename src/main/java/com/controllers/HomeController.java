package com.controllers;

import java.io.IOException;

import com.techprep.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class HomeController {

    @FXML
    private void goToLogin(ActionEvent event) throws IOException{
        App.setRoot("login"); 
    }

    @FXML
    private void goToLoginFromNav(MouseEvent event) throws IOException {
        App.setRoot("login");
    }

}