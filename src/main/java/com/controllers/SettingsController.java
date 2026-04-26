package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.Account;
import com.model.QuestionFacade;
import com.techprep.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the settings view; manages user profile editing and application preferences.
 * @author Coffee Coders
 */
public class SettingsController implements Initializable {
    private QuestionFacade qFacade;
    private Account account;

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField usernameField;
    @FXML private CheckBox fullscreenCheck;
    @FXML private Label statusLabel;

    /**
     * Controller initialization hook used to load current account data into form fields.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        qFacade = QuestionFacade.getInstance();
        account = qFacade.getCurrentAccount();

        if (account == null) {
            statusLabel.setText("No active session.");
            disableProfileFields(true);
            return;
        }

        firstNameField.setText(safe(account.getFirstName()));
        lastNameField.setText(safe(account.getLastName()));
        emailField.setText(safe(account.getEmail()));
        usernameField.setText(safe(account.getUsername()));

        if (fullscreenCheck != null && fullscreenCheck.getScene() != null) {
            Stage stage = (Stage) fullscreenCheck.getScene().getWindow();
            if (stage != null) {
                fullscreenCheck.setSelected(stage.isFullScreen());
            }
        }
    }

    private void disableProfileFields(boolean disabled) {
        firstNameField.setDisable(disabled);
        lastNameField.setDisable(disabled);
        emailField.setDisable(disabled);
        usernameField.setDisable(disabled);
        fullscreenCheck.setDisable(disabled);
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    @FXML
    private void goToDashboard(ActionEvent event) throws IOException {
        App.setRoot("dashboard");
    }

    @FXML
    private void goToQuestions(ActionEvent event) throws IOException {
        App.setRoot("question_list");
    }

    @FXML
    private void goToFavorites(ActionEvent event) throws IOException {
        App.setRoot("favorites");
    }

    @FXML
    private void goToCreate(ActionEvent event) throws IOException {
        App.setRoot("create_question");
    }

    @FXML
    private void saveProfile(ActionEvent event) {
        if (account == null) {
            statusLabel.setText("No active session.");
            return;
        }

        String updatedUsername = usernameField.getText() == null ? "" : usernameField.getText().trim();
        if (updatedUsername.isEmpty()) {
            statusLabel.setText("Username cannot be empty.");
            return;
        }

        String currentPassword = account.getPassword() == null ? "" : account.getPassword();
        qFacade.editAccount(
            firstNameField.getText(),
            lastNameField.getText(),
            updatedUsername,
            currentPassword,
            emailField.getText()
        );

        statusLabel.setText("Profile saved.");
    }

    @FXML
    private void toggleFullscreen(ActionEvent event) {
        Stage stage = (Stage) fullscreenCheck.getScene().getWindow();
        if (stage == null) {
            return;
        }

        stage.setFullScreen(fullscreenCheck.isSelected());
        statusLabel.setText(fullscreenCheck.isSelected() ? "Fullscreen enabled." : "Fullscreen disabled.");
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        qFacade.logout();
        App.setRoot("home");
    }
}
