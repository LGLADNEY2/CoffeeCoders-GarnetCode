package com.controllers;

import com.techprep.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.Objects;

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