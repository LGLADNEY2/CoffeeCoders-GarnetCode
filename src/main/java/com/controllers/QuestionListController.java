package com.controllers;

import java.net.URL;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.model.Course;
import com.model.Language;
import com.model.Question;
import com.model.QuestionList;
import com.model.QuestionTag;
import com.techprep.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class QuestionListController implements Initializable {

    @FXML
    private VBox questionRows;

    @FXML
    private void goToDashboard(ActionEvent event) throws IOException {
        App.setRoot("dashboard");
    }

    @FXML
    private void goToQuestions(ActionEvent event) throws IOException {
        App.setRoot("question_list");
    }

    @FXML
    private void goToCreate(ActionEvent event) throws IOException {
        App.setRoot("create_question");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Question> questions = QuestionList.getInstance().getQuestions();
        if (questions == null || questions.isEmpty()) {
            questionRows.getChildren().add(createEmptyState());
            return;
        }

        for (Question question : questions) {
            questionRows.getChildren().add(createQuestionRow(question));
        }
    }

    private VBox createEmptyState() {
        VBox emptyState = new VBox();
        emptyState.setStyle("-fx-background-color: #171313; -fx-background-radius: 18; -fx-padding: 22 18;");

        Label label = new Label("No questions found.");
        label.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        emptyState.getChildren().add(label);
        return emptyState;
    }

    private HBox createQuestionRow(Question question) {
        HBox row = new HBox(18.0);
        row.setStyle("-fx-background-color: #171313; -fx-background-radius: 18; -fx-padding: 14 18;");

        VBox titleBox = new VBox(4.0);
        titleBox.setPrefWidth(260.0);
        HBox.setHgrow(titleBox, javafx.scene.layout.Priority.ALWAYS);

        Label titleLabel = new Label(question.getTitle());
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        Label dateLabel = new Label(question.getDatePosted());
        dateLabel.setStyle("-fx-text-fill: #c9c9c9; -fx-font-size: 10px;");

        titleBox.getChildren().addAll(titleLabel, dateLabel);

        row.getChildren().add(titleBox);
        row.getChildren().add(createTagLabel(joinCategories(question.getQuestionTag())));
        row.getChildren().add(createTagLabel(joinLanguages(question.getQuestionTag())));
        row.getChildren().add(createTagLabel(joinCourses(question.getQuestionTag())));
        row.getChildren().add(createDifficultyLabel(question));

        Label rating = new Label(formatRating(question.getRating()));
        rating.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        row.getChildren().add(rating);

        return row;
    }

    private Label createTagLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: #ffd6d6; -fx-font-size: 10px; -fx-background-color: #6b2323; -fx-background-radius: 999; -fx-padding: 4 10;");
        return label;
    }

    private Label createDifficultyLabel(Question question) {
        Label label = new Label(question.getDifficulty().toString());
        String difficulty = question.getDifficulty().toString().toUpperCase();
        String color;
        if ("BEGINNER".equals(difficulty)) {
            color = "#4f8f37";
        } else if ("INTERMEDIATE".equals(difficulty)) {
            color = "#d8a33c";
        } else {
            color = "#d85c43";
        }
        label.setStyle("-fx-text-fill: white; -fx-font-size: 10px; -fx-background-color: " + color + "; -fx-background-radius: 999; -fx-padding: 4 10;");
        return label;
    }

    private String joinLanguages(QuestionTag questionTag) {
        if (questionTag == null || questionTag.getLanguage() == null || questionTag.getLanguage().isEmpty()) {
            return "JAVA";
        }

        ArrayList<String> languages = new ArrayList<>();
        for (Language language : questionTag.getLanguage()) {
            languages.add(language.toString());
        }
        return String.join(", ", languages);
    }

    private String joinCategories(QuestionTag questionTag) {
        if (questionTag == null || questionTag.getCategory() == null || questionTag.getCategory().isEmpty()) {
            return "CLASS";
        }

        ArrayList<String> categories = new ArrayList<>();
        for (com.model.Category category : questionTag.getCategory()) {
            categories.add(category.toString());
        }
        return String.join(", ", categories);
    }

    private String joinCourses(QuestionTag questionTag) {
        if (questionTag == null || questionTag.getCourse() == null || questionTag.getCourse().isEmpty()) {
            return "COURSE";
        }

        ArrayList<String> courses = new ArrayList<>();
        for (Course course : questionTag.getCourse()) {
            courses.add(course.toString());
        }
        return String.join(", ", courses);
    }

    private String formatRating(float rating) {
        if (rating < 0) {
            return "0.0";
        }
        return String.format("%.1f", rating);
    }
}