package com.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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

    @FXML
    private void initialize() {
        
    }

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
