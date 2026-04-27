package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.model.Account;
import com.model.Question;
import com.model.QuestionFacade;
import com.model.Role;
import com.model.Student;
import com.techprep.App;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

/**
 * Controller for the dashboard view. Manages user profile display,
 * multiple question tables (continue, recommended, favorites), and
 * user actions such as logout and navigation.
 * @author Coffee Coders
 */
public class DashboardController implements Initializable{
    private QuestionFacade qFacade;
    private Account account;
    @FXML private Label completedQuestions;
    @FXML private Label dailyStreak;
    @FXML private TableView<Question> continueTable;
    @FXML private TableColumn<Question, String> conTitleDate;
    @FXML private TableColumn<Question, String> conCategory;
    @FXML private TableColumn<Question, String> conLanguage;
    @FXML private TableColumn<Question, String> conClass;
    @FXML private TableColumn<Question, String> conDifficulty;
    @FXML private TableColumn<Question, String> conRating;

    // Recommended table and its columns
    @FXML private TableView<Question> recommendedTable;
    @FXML private TableColumn<Question, String> recTitleDate;
    @FXML private TableColumn<Question, String> recCategory;
    @FXML private TableColumn<Question, String> recLanguage;
    @FXML private TableColumn<Question, String> recClass;
    @FXML private TableColumn<Question, String> recDifficulty;
    @FXML private TableColumn<Question, String> recRating;

    // Favorites table and its columns
    @FXML private TableView<Question> favoritesTable;
    @FXML private TableColumn<Question, String> favTitleDate;
    @FXML private TableColumn<Question, String> favCategory;
    @FXML private TableColumn<Question, String> favLanguage;
    @FXML private TableColumn<Question, String> favClass;
    @FXML private TableColumn<Question, String> favDifficulty;
    @FXML private TableColumn<Question, String> favRating;
    @FXML private ImageView profileImage;


    /**
     * Navigate to the create-question view.
     *
     * @param event UI action event
     * @throws IOException when the FXML cannot be loaded
     */
    @FXML
    private void goToCreate(ActionEvent event) throws IOException {
        App.setRoot("create_question");
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
     * Display a placeholder settings dialog.
     *
     * @param event UI action event
     */
    @FXML
    private void openSettings(ActionEvent event) throws IOException {
        App.setRoot("settings");
    }

    /**
     * Logout the current account and return to the home view.
     *
     * @param event UI action event
     * @throws IOException when the FXML cannot be loaded
     */
    @FXML
    private void logout(ActionEvent event) throws IOException {
        qFacade.logout();
        App.setRoot("home");
    }

    /**
     * Helper to configure six table columns at once for the UI tables.
     *
     * @param col0 title/date column
     * @param col1 category column
     * @param col2 language column
     * @param col3 class/course column
     * @param col4 difficulty column
     * @param col5 rating column
     */
    private void configureTableColumns(
        TableColumn<Question, String> titleDateCol,
        TableColumn<Question, String> categoryCol,
        TableColumn<Question, String> languageCol,
        TableColumn<Question, String> classCol,
        TableColumn<Question, String> difficultyCol,
        TableColumn<Question, String> ratingCol) {
        
        titleDateCol.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getTitle() + "\n" +
                    cellData.getValue().formatDate(cellData.getValue().getDatePosted())));

        categoryCol.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getQuestionTag().getCategory().get(0).toString()));

        languageCol.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getQuestionTag().getLanguage().toString()));

        classCol.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getQuestionTag().getCourse().get(0).toString()));

        difficultyCol.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getDifficulty().toString()));

        ratingCol.setCellValueFactory(cellData ->
            new SimpleStringProperty(String.valueOf(cellData.getValue().getRating())));

        titleDateCol.setPrefWidth(320);
        categoryCol.setPrefWidth(140);
        languageCol.setPrefWidth(120);
        classCol.setPrefWidth(120);
        difficultyCol.setPrefWidth(140);
        ratingCol.setPrefWidth(90);

        titleDateCol.setMinWidth(220);
        categoryCol.setMinWidth(110);
        languageCol.setMinWidth(95);
        classCol.setMinWidth(95);
        difficultyCol.setMinWidth(110);
        ratingCol.setMinWidth(70);
    }

    private void setupTableClickHandler(TableView<Question> table) {
    table.setRowFactory(tv -> {
        TableRow<Question> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !row.isEmpty()) {  // double‑click
                Question selectedQuestion = row.getItem();
                if (selectedQuestion != null) {
                    // Store the question so the detail page can access it
                    qFacade.getQuestion(selectedQuestion.getQuestionID());
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
     * Initializes dashboard controls, configures tables and loads account data.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        qFacade = QuestionFacade.getInstance();

        this.account = qFacade.getCurrentAccount();
        loadProfileImage();

        setupTableClickHandler(continueTable);
        setupTableClickHandler(recommendedTable);
        setupTableClickHandler(favoritesTable);

        configureTableColumns(conTitleDate, conCategory, conLanguage, 
                            conClass, conDifficulty, conRating);
        configureTableColumns(recTitleDate, recCategory, recLanguage, 
                            recClass, recDifficulty, recRating);
        configureTableColumns(favTitleDate, favCategory, favLanguage, 
                            favClass, favDifficulty, favRating);

        continueTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        recommendedTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        favoritesTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        if (account.getRole() == Role.STUDENT) {
            Student student = (Student) account;
            student.updateDailyStreak();
            completedQuestions.setText(String.valueOf(student.getCompletedQuestions().size()));
            dailyStreak.setText(String.valueOf(student.getDailyStreak()));

            continueTable.setItems(FXCollections.observableArrayList(student.getCompletedQuestions()));
            favoritesTable.setItems(FXCollections.observableArrayList(student.getFavoriteQuestions()));

            // Recommended = all questions not already completed
            List<Question> recommended = qFacade.getQuestions().stream().filter(q -> !student.getCompletedQuestions().contains(q)).collect(Collectors.toList());
            recommendedTable.setItems(FXCollections.observableArrayList(recommended));
        }
    }

    /**
     * Loads and configures the profile image displayed on the dashboard.
     */
    private void loadProfileImage() {
        if (profileImage == null) {
            return;
        }

        Image image = null;
        String[] candidatePaths = {
            "/com/techprep/images/CockyPFP.jpg",
            "/com/techprep/images/uofsccampusvillage.jpg"
        };

        for (String path : candidatePaths) {
            URL resource = getClass().getResource(path);
            if (resource != null) {
                image = new Image(resource.toExternalForm());
                break;
            }
        }

        if (image != null && !image.isError()) {
            profileImage.setImage(image);
        }
        profileImage.setPreserveRatio(true);
        profileImage.setSmooth(true);

        Circle clip = new Circle(31, 31, 31);
        profileImage.setClip(clip);
    }
}
