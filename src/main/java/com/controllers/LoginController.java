package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.QuestionFacade;
import com.techprep.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * Controller for the login view; handles login flow and navigation.
 * @author Coffee Coders
 */
public class LoginController implements  Initializable{
    public QuestionFacade qFacade;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_username;

    @FXML
    private Label lbl_loginError;

    /**
     * Navigate back to the home view.
     *
     * @param event UI action event
     * @throws IOException when the FXML cannot be loaded
     */
    @FXML
    private void back(ActionEvent event) throws IOException {
        App.setRoot("home");
    }

    /**
     * Open the signup view.
     *
     * @param event UI action event
     * @throws IOException when the FXML cannot be loaded
     */
    @FXML
    private void goToSignup(ActionEvent event) throws IOException {
        App.setRoot("signup");
    }

    /**
     * Attempt to authenticate and navigate to the dashboard on success.
     *
     * @param event mouse event from the login button
     * @throws IOException when the FXML cannot be loaded
     */
    @FXML
    private void btn_Login(MouseEvent event) throws IOException{
        String username = txt_username.getText().trim();
        String password = txt_password.getText().trim();
        
        if(!qFacade.login(username, password)){
            lbl_loginError.setText("Invalid Login, Please Try Again");
            txt_username.clear();
            txt_password.clear();
            return;
        }
        lbl_loginError.setText("");
        App.setRoot("dashboard");
    }

    /**
     * Controller initialization hook used to obtain facade instance.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        qFacade = QuestionFacade.getInstance();
    }

}