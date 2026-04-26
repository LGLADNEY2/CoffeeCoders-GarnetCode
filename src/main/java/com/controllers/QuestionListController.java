package com.controllers;

import java.net.URL;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.model.Course;
import com.model.Language;
import com.model.Question;
import com.model.QuestionFacade;
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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

/**
 * Controller for the question list view. Configures the question table
 * and populates rows from the application's question list.
 * @author Coffee Coders
 */
public class QuestionListController implements Initializable {
    private final QuestionFacade qFacade = QuestionFacade.getInstance();

    @FXML private TableView<ObservableList<String>> questionTable;
    @FXML private TableColumn<ObservableList<String>, String> listTitleDate;
    @FXML private TableColumn<ObservableList<String>, String> listCategory;
    @FXML private TableColumn<ObservableList<String>, String> listLanguage;
    @FXML private TableColumn<ObservableList<String>, String> listClass;
    @FXML private TableColumn<ObservableList<String>, String> listDifficulty;
    @FXML private TableColumn<ObservableList<String>, String> listRating;

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
     * Refreshes/opens the questions list view.
     *
     * @param event UI action event
     * @throws IOException when the FXML cannot be loaded
     */
    @FXML
    private void goToQuestions(ActionEvent event) throws IOException {
        App.setRoot("question_list");
    }

    /**
     * Open the favorites view.
     *
     * @param event UI action event
     * @throws IOException when the FXML cannot be loaded
     */
    @FXML
    private void goToFavorites(ActionEvent event) throws IOException {
        App.setRoot("favorites");
    }

    /**
     * Navigates to the create-question view.
     *
     * @param event UI action event
     * @throws IOException when the FXML cannot be loaded
     */
    @FXML
    private void goToCreate(ActionEvent event) throws IOException {
        App.setRoot("create_question");
    }

    /**
     * Configures table columns, cell factories and cell value mapping
     * for the question table.
     */
    private void configureTableColumns() {
        questionTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        questionTable.setFixedCellSize(56);

        listTitleDate.setPrefWidth(320);
        listCategory.setPrefWidth(140);
        listLanguage.setPrefWidth(120);
        listClass.setPrefWidth(120);
        listDifficulty.setPrefWidth(140);
        listRating.setPrefWidth(90);

        listTitleDate.setMinWidth(220);
        listCategory.setMinWidth(110);
        listLanguage.setMinWidth(95);
        listClass.setMinWidth(95);
        listDifficulty.setMinWidth(110);
        listRating.setMinWidth(70);

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

        questionTable.setRowFactory(tv -> {
            TableRow<ObservableList<String>> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    int index = row.getIndex();
                    ArrayList<Question> questions = QuestionList.getInstance().getQuestions();
                    if (index >= 0 && index < questions.size()) {
                        qFacade.getQuestion(questions.get(index).getQuestionID());
                        try {
                            App.setRoot("question_detail");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            return row;
        });
    }

    /**
     * Adds a question as a row in the questions table.
     *
     * @param question question to add
     */
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

    /**
     * Initializes the controller after its root element has been
     * completely processed. Populates the table with available questions.
     */
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

    /**
     * Returns a comma-separated string of language tags for the given question tag.
     *
     * @param questionTag tag containing language data
     * @return joined language string
     */
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

    /**
     * Returns a comma-separated string of category tags for the given question tag.
     *
     * @param questionTag tag containing category data
     * @return joined category string
     */
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

    /**
     * Returns a comma-separated string of course tags for the given question tag.
     *
     * @param questionTag tag containing course data
     * @return joined course string
     */
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

    /**
     * Formats the question rating for display; normalizes negative values to 0.0.
     *
     * @param rating raw rating value
     * @return string formatted to one decimal place
     */
    private String formatRating(float rating) {
        if (rating < 0) {
            return "0.0";
        }
        return String.format("%.1f", rating);
    }
}