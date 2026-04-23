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
 * Controller for the signup view. Handles navigation from the signup screen.
 * @author Coffee Coders
 */
public class SignupController implements Initializable{
    public QuestionFacade qFacade;
    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_username;

    @FXML
    private TextField txt_name;

    @FXML
    private Label lbl_SignupError;


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

    @FXML
    private void btn_Signup(MouseEvent event) throws IOException{
        String username = txt_username.getText().trim();
        String password = txt_password.getText().trim();
        String name = txt_name.getText().trim();
        if(!name.contains(" ")) {
            lbl_SignupError.setText("Invalid Name, Please Try Again");
            txt_name.clear();
            return;
        }
        int firstSpace = name.indexOf(' ');
        String firstName = name.substring(0, firstSpace);
        String lastName = name.substring(firstSpace + 1);
        if(!qFacade.addAccount(firstName, lastName, "temp@email.com", username, password)){
            lbl_SignupError.setText("Invalid Signup, Please Try Again");
            txt_username.clear();
            txt_password.clear();
            txt_name.clear();
            return;
        }
        lbl_SignupError.setText("");
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
