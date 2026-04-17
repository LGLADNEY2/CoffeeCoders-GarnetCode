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

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class QuestionListController implements Initializable {

    @FXML private TableView<ObservableList<String>> questionTable;
    @FXML private TableColumn<ObservableList<String>, String> listTitleDate;
    @FXML private TableColumn<ObservableList<String>, String> listCategory;
    @FXML private TableColumn<ObservableList<String>, String> listLanguage;
    @FXML private TableColumn<ObservableList<String>, String> listClass;
    @FXML private TableColumn<ObservableList<String>, String> listDifficulty;
    @FXML private TableColumn<ObservableList<String>, String> listRating;

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

    private void configureTableColumns() {
        questionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        listTitleDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
        listCategory.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
        listLanguage.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));
        listClass.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));
        listDifficulty.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));
        listRating.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(5)));

        listTitleDate.setCellFactory(column -> new TableCell<>() {
            private final Label wrappedLabel = new Label();

            {
                wrappedLabel.setWrapText(true);
                wrappedLabel.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
                setPrefHeight(USE_COMPUTED_SIZE);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    return;
                }

                wrappedLabel.setText(item);
                wrappedLabel.setMaxWidth(Math.max(120, getTableColumn().getWidth() - 18));
                setGraphic(wrappedLabel);
            }
        });
    }

    private void addRow(Question question) {
        ObservableList<String> row = FXCollections.observableArrayList(
            question.getTitle() + "\n" + question.getDatePosted(),
            joinCategories(question.getQuestionTag()),
            joinLanguages(question.getQuestionTag()),
            joinCourses(question.getQuestionTag()),
            question.getDifficulty().toString(),
            formatRating(question.getRating())
        );
        questionTable.getItems().add(row);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTableColumns();
        questionTable.setItems(FXCollections.observableArrayList());

        ArrayList<Question> questions = QuestionList.getInstance().getQuestions();
        if (questions == null || questions.isEmpty()) {
            questionTable.setPlaceholder(new Label("No questions found."));
            return;
        }

        for (Question question : questions) {
            addRow(question);
        }
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