package com.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

import com.techprep.App;

/**
 * Controller for the create-question view. Collects input fields and
 * handles submission/navigation actions.
 * @author Coffee Coders
 */
public class CreateQuestionController {
    @FXML private TextField titleField;
    @FXML private TextField timeField;
    @FXML private TextField difficultyField;
    @FXML private TextArea descriptionField;
    @FXML private TextField hint1Field;
    @FXML private TextField hint2Field;
    @FXML private TextField hint3Field;
    @FXML private TextArea solutionField;
    @FXML private Button submitButton;

    /**
     * Initialization hook for the create-question view.
     */
    @FXML
    private void initialize() {
        
    }

    /**
     * Navigate to the dashboard view.
     *
     * @param event UI action event
     * @throws IOException when the FXML cannot be loaded
     */
    @FXML
    private void goToDashboard(ActionEvent event) throws IOException {
        App.setRoot("dashboard");
    }

    /**
     * Open the question list view.
     *
     * @param event UI action event
     * @throws IOException when the FXML cannot be loaded
     */
    @FXML
    private void goToQuestions(ActionEvent event) throws IOException {
        App.setRoot("question_list");
    }

    /**
     * Handle the submit action for creating a new question. Currently
     * logs the captured input values to stdout.
     *
     * @param event UI action event
     */
    @FXML
    private void handleSubmit(ActionEvent event) {
        String title = titleField.getText();
        String time = timeField.getText();
        String difficulty = difficultyField.getText();
        String description = descriptionField.getText();
        String hint1 = hint1Field.getText();
        String hint2 = hint2Field.getText();
        String hint3 = hint3Field.getText();
        String solution = solutionField.getText();
        
        System.out.println("Submitted Question: " + title + ", " + time + ", " + difficulty);
    }
}
