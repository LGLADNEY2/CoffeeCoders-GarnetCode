package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.QuestionFacade;
import com.techprep.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController implements  Initializable{
    public QuestionFacade qFacade;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_username;

    @FXML
    private void back(ActionEvent event) throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void goToSignup(ActionEvent event) throws IOException {
        App.setRoot("signup");
    }

    @FXML
    private void btn_Login(MouseEvent event) throws IOException{
        String username = txt_username.getText();
        String password = txt_password.getText();
        
        if(!qFacade.login(username, password)){
            return;
        }
        qFacade.login(username, password);
        App.setRoot("dashboard");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        qFacade = QuestionFacade.getInstance();
    }

}