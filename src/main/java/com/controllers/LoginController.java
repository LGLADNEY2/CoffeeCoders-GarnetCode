package com.controllers;

import java.io.IOException;

import com.model.QuestionFacade;
import com.techprep.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private Button btn_login;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_username;

    @FXML
    void back(ActionEvent event) throws IOException {
        App.setRoot("home");
    }

    @FXML
    void goToSignup(ActionEvent event) throws IOException {
        App.setRoot("signup");
    }

    @FXML
    void login(ActionEvent event) {
        String userName = txt_username.getText();
        String password = txt_password.getText();
        System.out.println("Your name is " + userName + " pass " + password);
        QuestionFacade facade = QuestionFacade.getInstance();
        //f.addAccount("temp", "temp", userName, password, "temp");
        if(facade.login(userName, password)){
            System.out.println("Successfully logged in");
        } else {
            System.out.println("Error logging in");
        }
    }

}