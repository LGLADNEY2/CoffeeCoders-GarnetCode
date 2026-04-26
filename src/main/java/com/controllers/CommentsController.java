package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import com.model.Account;
import com.model.AccountList;
import com.model.Comment;
import com.model.Question;
import com.model.QuestionFacade;
import com.techprep.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Controller for a simple comments page that lists all comments
 * for the currently selected question.
 */
public class CommentsController implements Initializable {
    private final QuestionFacade qFacade = QuestionFacade.getInstance();
    private final Map<UUID, String> usernameById = new HashMap<>();

    private static final DateTimeFormatter STORED_DATE_FORMAT =
        DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter LEGACY_DATE_FORMAT =
        DateTimeFormatter.ofPattern("MMddyyyy");
    private static final DateTimeFormatter DISPLAY_DATE_FORMAT =
        DateTimeFormatter.ofPattern("MM/dd/yy");

    @FXML private Label commentsTitle;
    @FXML private VBox commentsList;

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
    private void goToQuestion(ActionEvent event) throws IOException {
        App.setRoot("question_detail");
    }

    @FXML
    private void goToSolution(ActionEvent event) throws IOException {
        App.setRoot("codeview");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        commentsList.getChildren().clear();
        loadUserMap();

        Question question = qFacade.getCurrentQuestion();
        if (question == null) {
            commentsTitle.setText("Comments");
            addInfoLabel("No question selected.");
            return;
        }

        commentsTitle.setText("Comments - " + question.getTitle());

        if (question.getComments() == null || question.getComments().isEmpty()) {
            addInfoLabel("No comments yet.");
            return;
        }

        for (Comment comment : question.getComments()) {
            commentsList.getChildren().add(buildCommentCard(comment));
        }
    }

    private VBox buildCommentCard(Comment comment) {
        VBox commentCard = new VBox();
        commentCard.setSpacing(8);
        commentCard.setStyle("-fx-background-color: #221f1f; -fx-background-radius: 10; -fx-padding: 10 12 10 12;");

        Label meta = new Label(resolveUsername(comment.getAccountID()) + " • " + formatDate(comment.getDatePosted()));
        meta.setStyle("-fx-text-fill: #b8b8b8; -fx-font-size: 11px;");

        Label body = new Label(comment.getText());
        body.setWrapText(true);
        body.setStyle("-fx-text-fill: #efefef; -fx-font-size: 13px;");

        Label likes = new Label("Likes: " + comment.getLikes());
        likes.setStyle("-fx-text-fill: #a7a7a7; -fx-font-size: 11px;");

        HBox actions = new HBox();
        actions.setSpacing(8);
        actions.setAlignment(Pos.CENTER_LEFT);

        Button likeButton = new Button("Like");
        Button dislikeButton = new Button("Dislike");
        Button replyButton = new Button("Reply");
        likeButton.setStyle("-fx-background-color: #2d6b3f; -fx-text-fill: #ffffff; -fx-font-size: 11px; -fx-background-radius: 6; -fx-padding: 4 10;");
        dislikeButton.setStyle("-fx-background-color: #5f2b2b; -fx-text-fill: #ffffff; -fx-font-size: 11px; -fx-background-radius: 6; -fx-padding: 4 10;");
        replyButton.setStyle("-fx-background-color: #3a3f50; -fx-text-fill: #ffffff; -fx-font-size: 11px; -fx-background-radius: 6; -fx-padding: 4 10;");

        final int[] displayedLikes = new int[] { comment.getLikes() };

        likeButton.setOnAction(event -> {
            comment.like();
            displayedLikes[0] = comment.getLikes();
            likes.setText("Likes: " + displayedLikes[0]);
        });

        // Dislike affects the visible count in this simple page view.
        dislikeButton.setOnAction(event -> {
            if (displayedLikes[0] > 0) {
                displayedLikes[0]--;
                likes.setText("Likes: " + displayedLikes[0]);
            }
        });

        VBox repliesBox = new VBox();
        repliesBox.setSpacing(6);
        renderReplies(comment, repliesBox);

        HBox replyComposer = new HBox();
        replyComposer.setSpacing(6);
        replyComposer.setVisible(false);
        replyComposer.setManaged(false);

        TextField replyField = new TextField();
        replyField.setPromptText("Write a reply...");
        replyField.setStyle("-fx-background-color: #1a1a1a; -fx-text-fill: #efefef; -fx-prompt-text-fill: #888888; -fx-background-radius: 6; -fx-padding: 4 8;");

        Button postReply = new Button("Post");
        postReply.setStyle("-fx-background-color: #2d6b3f; -fx-text-fill: #ffffff; -fx-font-size: 11px; -fx-background-radius: 6; -fx-padding: 4 10;");

        replyButton.setOnAction(event -> {
            boolean newVisibility = !replyComposer.isVisible();
            replyComposer.setVisible(newVisibility);
            replyComposer.setManaged(newVisibility);
            if (newVisibility) {
                replyField.requestFocus();
            }
        });

        postReply.setOnAction(event -> {
            String replyText = replyField.getText() == null ? "" : replyField.getText().trim();
            if (replyText.isEmpty()) {
                return;
            }

            UUID currentUserId = qFacade.getCurrentAccount() != null
                ? qFacade.getCurrentAccount().getAccountID()
                : null;
            if (currentUserId == null) {
                return;
            }

            if (comment.addReply(currentUserId, replyText)) {
                replyField.clear();
                replyComposer.setVisible(false);
                replyComposer.setManaged(false);
                renderReplies(comment, repliesBox);
            }
        });

        actions.getChildren().addAll(likeButton, dislikeButton, replyButton);
        replyComposer.getChildren().addAll(replyField, postReply);

        commentCard.getChildren().addAll(meta, body, likes, actions, replyComposer, repliesBox);
        return commentCard;
    }

    private void loadUserMap() {
        usernameById.clear();
        for (Account account : AccountList.getInstance().getAccounts()) {
            usernameById.put(account.getAccountID(), account.getUsername());
        }
    }

    private String resolveUsername(UUID accountId) {
        if (accountId == null) {
            return "unknown";
        }
        return usernameById.getOrDefault(accountId, accountId.toString());
    }

    private String formatDate(String storedDate) {
        if (storedDate == null || storedDate.isBlank()) {
            return "--/--/--";
        }

        String trimmedDate = storedDate.trim();

        // New comments use Date.toString(), older seeded data uses MMddyyyy digits.
        if (trimmedDate.matches("\\d{8}")) {
            try {
                LocalDate parsedLegacy = LocalDate.parse(trimmedDate, LEGACY_DATE_FORMAT);
                return parsedLegacy.format(DISPLAY_DATE_FORMAT);
            } catch (DateTimeParseException ignored) {
                // Fall through to alternate parse attempts.
            }
        }

        try {
            ZonedDateTime parsed = ZonedDateTime.parse(trimmedDate, STORED_DATE_FORMAT);
            return parsed.format(DISPLAY_DATE_FORMAT);
        } catch (DateTimeParseException ex) {
            return "--/--/--";
        }
    }

    private void renderReplies(Comment comment, VBox repliesBox) {
        repliesBox.getChildren().clear();

        if (comment.getReplies() == null || comment.getReplies().isEmpty()) {
            return;
        }

        for (Comment reply : comment.getReplies()) {
            Label replyLabel = new Label(
                "↳ " + resolveUsername(reply.getAccountID())
                    + " (" + formatDate(reply.getDatePosted()) + "): "
                    + reply.getText()
            );
            replyLabel.setWrapText(true);
            replyLabel.setStyle("-fx-text-fill: #d6d6d6; -fx-font-size: 12px; -fx-padding: 2 0 0 12;");
            repliesBox.getChildren().add(replyLabel);
        }
    }

    private void addInfoLabel(String message) {
        Label label = new Label(message);
        label.setStyle("-fx-text-fill: #d0d0d0; -fx-font-size: 13px;");
        commentsList.getChildren().add(label);
    }
}
