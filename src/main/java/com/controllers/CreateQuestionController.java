package com.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

import com.techprep.App;
import com.model.QuestionFacade;
import com.model.Difficulty;
import com.model.QuestionTag;
import com.model.Segment;
import com.model.DataType;
import com.model.Category;
import com.model.Language;
import com.model.Course;
import com.model.Question;

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

        if (title == null || title.isBlank()) {
            System.out.println("Cannot submit: title is required.");
            return;
        }

        int recTime = -1;
        try {
            if (time != null && !time.isBlank()) recTime = Integer.parseInt(time.trim());
        } catch (NumberFormatException e) {
            recTime = -1;
        }

        Difficulty diff = Difficulty.BEGINNER;
        try {
            if (difficulty != null && !difficulty.isBlank())
                diff = Difficulty.valueOf(difficulty.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            diff = Difficulty.BEGINNER;
        }

        ArrayList<Segment> segments = new ArrayList<>();
        if (description != null && !description.isBlank()) {
            segments.add(new Segment("Description", description, DataType.STRING, ""));
        } else {
            segments.add(new Segment("Description", "No description provided.", DataType.STRING, ""));
        }

        ArrayList<Segment> hints = new ArrayList<>();
        if (hint1 != null && !hint1.isBlank()) hints.add(new Segment("Hint 1", hint1, DataType.HINT, ""));
        if (hint2 != null && !hint2.isBlank()) hints.add(new Segment("Hint 2", hint2, DataType.HINT, ""));
        if (hint3 != null && !hint3.isBlank()) hints.add(new Segment("Hint 3", hint3, DataType.HINT, ""));

        ArrayList<Category> categories = new ArrayList<>();
        categories.add(Category.CLASS);
        ArrayList<Language> languages = new ArrayList<>();
        languages.add(Language.JAVA);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(Course.CSCE_247);
        QuestionTag tag = new QuestionTag(categories, languages, courses);

        QuestionFacade qf = QuestionFacade.getInstance();

        // Allow submission even when not logged in; facade will use a generated author id.
        Question created = qf.addQuestion(title, diff, tag, segments, hints, recTime);
        if (created != null) {
            System.out.println("Submitted Question: " + title + " (id=" + created.getQuestionID() + ")");
            try {
                App.setRoot("question_list");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to create question.");
        }
    }
}
