package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.model.Question;
import com.model.QuestionFacade;
import com.model.Segment;
import com.techprep.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Controller for the question detail page.
 */
public class QuestionDetailController implements Initializable {
    private final QuestionFacade qFacade = QuestionFacade.getInstance();

    @FXML private Label titleLabel;
    @FXML private Label difficultyLabel;
    @FXML private Label metadataLabel;
    @FXML private Label detailText;
    @FXML private Label examplesText;
    @FXML private Label constraintsText;
    @FXML private VBox hintsBox;

    @FXML
    private void goToDashboard(ActionEvent event) throws IOException {
        App.setRoot("dashboard");
    }

    @FXML
    private void goToFavorites(ActionEvent event) throws IOException {
        App.setRoot("favorites");
    }

    @FXML
    private void goToQuestions(ActionEvent event) throws IOException {
        App.setRoot("question_list");
    }

    @FXML
    private void goToCreate(ActionEvent event) throws IOException {
        App.setRoot("create_question");
    }

    @FXML
    private void goToSolution(ActionEvent event) throws IOException {
        App.setRoot("codeview");
    }

    @FXML
    private void goToComments(ActionEvent event) throws IOException {
        App.setRoot("comments");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Question question = qFacade.getCurrentQuestion();
        if (question == null) {
            titleLabel.setText("Question not found");
            difficultyLabel.setText("UNKNOWN");
            metadataLabel.setText("No question selected.");
            detailText.setText("Select a question from dashboard or favorites to view details.");
            examplesText.setText("No examples available.");
            constraintsText.setText("No constraints available.");
            return;
        }

        titleLabel.setText(safe(question.getTitle(), "Untitled Question"));
        difficultyLabel.setText(safe(question.getDifficulty() != null ? question.getDifficulty().toString() : null, "UNKNOWN"));

        String timeText = question.getRecommendedTime() > 0
            ? question.getRecommendedTime() + " min"
            : "N/A min";
        metadataLabel.setText(timeText + "   •   " + String.format("%.1f", question.getRating()) + " rating");

        detailText.setText(extractSegment(question.getSegments(), "detail", "description", "problem", "No details available."));
        examplesText.setText(extractSegment(question.getSegments(), "example", "Examples are not available for this question yet."));
        constraintsText.setText(extractSegment(question.getSegments(), "constraint", "Constraints are not available for this question yet."));

        renderHints(question.getHints());
    }

    private void renderHints(ArrayList<Segment> hints) {
        hintsBox.getChildren().clear();

        if (hints == null || hints.isEmpty()) {
            Label emptyHint = new Label("No hints available.");
            emptyHint.setStyle("-fx-text-fill: #bdbdbd; -fx-font-size: 13px;");
            hintsBox.getChildren().add(emptyHint);
            return;
        }

        for (Segment hint : hints) {
            Label hintLabel = new Label("• " + safe(hint.getDesc(), ""));
            hintLabel.setWrapText(true);
            hintLabel.setStyle("-fx-text-fill: #e8e8e8; -fx-font-size: 13px;");
            hintsBox.getChildren().add(hintLabel);
        }
    }

    private String extractSegment(ArrayList<Segment> segments, String keyword, String fallback) {
        if (segments == null || segments.isEmpty()) {
            return fallback;
        }

        for (Segment segment : segments) {
            String title = segment.getTitle() == null ? "" : segment.getTitle().toLowerCase();
            if (title.contains(keyword)) {
                return safe(segment.getDesc(), fallback);
            }
        }

        return fallback;
    }

    private String extractSegment(ArrayList<Segment> segments, String keyword1, String keyword2, String keyword3, String fallback) {
        if (segments == null || segments.isEmpty()) {
            return fallback;
        }

        for (Segment segment : segments) {
            String title = segment.getTitle() == null ? "" : segment.getTitle().toLowerCase();
            if (title.contains(keyword1) || title.contains(keyword2) || title.contains(keyword3)) {
                return safe(segment.getDesc(), fallback);
            }
        }

        return safe(segments.get(0).getDesc(), fallback);
    }

    private String safe(String value, String fallback) {
        return (value == null || value.isBlank()) ? fallback : value;
    }
}
