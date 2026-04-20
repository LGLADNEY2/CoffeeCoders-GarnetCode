package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import com.model.Account;
import com.model.Question;
import com.model.QuestionFacade;
import com.model.Role;
import com.model.Student;
import com.techprep.App;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class DashboardController implements Initializable{
    private QuestionFacade qFacade;
    private Account account;
    @FXML private Label completedQuestions;
    @FXML private Label dailyStreak;
    @FXML private TableView<ObservableList<String>> continueTable;
    @FXML private TableColumn<ObservableList<String>, String> conTitleDate;
    @FXML private TableColumn<ObservableList<String>, String> conCategory;
    @FXML private TableColumn<ObservableList<String>, String> conLanguage;
    @FXML private TableColumn<ObservableList<String>, String> conClass;
    @FXML private TableColumn<ObservableList<String>, String> conDifficulty;
    @FXML private TableColumn<ObservableList<String>, String> conRating;

    // Recommended table and its columns
    @FXML private TableView<ObservableList<String>> recommendedTable;
    @FXML private TableColumn<ObservableList<String>, String> recTitleDate;
    @FXML private TableColumn<ObservableList<String>, String> recCategory;
    @FXML private TableColumn<ObservableList<String>, String> recLanguage;
    @FXML private TableColumn<ObservableList<String>, String> recClass;
    @FXML private TableColumn<ObservableList<String>, String> recDifficulty;
    @FXML private TableColumn<ObservableList<String>, String> recRating;

    // Favorites table and its columns
    @FXML private TableView<ObservableList<String>> favoritesTable;
    @FXML private TableColumn<ObservableList<String>, String> favTitleDate;
    @FXML private TableColumn<ObservableList<String>, String> favCategory;
    @FXML private TableColumn<ObservableList<String>, String> favLanguage;
    @FXML private TableColumn<ObservableList<String>, String> favClass;
    @FXML private TableColumn<ObservableList<String>, String> favDifficulty;
    @FXML private TableColumn<ObservableList<String>, String> favRating;
    @FXML private ImageView profileImage;


    @FXML
    private void goToCreate(ActionEvent event) throws IOException {
        App.setRoot("create_question");
    }

    @FXML
    private void goToQuestions(ActionEvent event) throws IOException {
        App.setRoot("question_list");
    }

    @FXML
    private void openSettings(ActionEvent event) {
        Alert settingsAlert = new Alert(Alert.AlertType.INFORMATION);
        settingsAlert.setTitle("Settings");
        settingsAlert.setHeaderText("Settings");
        settingsAlert.setContentText("Settings panel coming soon.");
        settingsAlert.showAndWait();
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        qFacade.logout();
        App.setRoot("home");
    }

    private void configureTableColumns(
            TableColumn<ObservableList<String>, String> col0,
            TableColumn<ObservableList<String>, String> col1,
            TableColumn<ObservableList<String>, String> col2,
            TableColumn<ObservableList<String>, String> col3,
            TableColumn<ObservableList<String>, String> col4,
            TableColumn<ObservableList<String>, String> col5) {
        
        col0.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
        col1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
        col2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));
        col3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));
        col4.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));
        col5.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(5)));
    }

    private void addRow(TableView<ObservableList<String>> table, String titleDate, String category, 
                        String language, String course, String difficulty, String rating) {
        ObservableList<String> row = FXCollections.observableArrayList(
                titleDate, category, language, course, difficulty, rating);
        table.getItems().add(row);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        qFacade = QuestionFacade.getInstance();

        this.account = qFacade.getCurrentAccount();
        loadProfileImage();

         // Configure columns for all three tables
        configureTableColumns(conTitleDate, conCategory, conLanguage, conClass, conDifficulty, conRating);
        configureTableColumns(recTitleDate, recCategory, recLanguage, recClass, recDifficulty, recRating);
        configureTableColumns(favTitleDate, favCategory, favLanguage, favClass, favDifficulty, favRating);

        // Initialize tables with empty data
        continueTable.setItems(FXCollections.observableArrayList());
        recommendedTable.setItems(FXCollections.observableArrayList());
        favoritesTable.setItems(FXCollections.observableArrayList());

        if (account.getRole() == Role.STUDENT) {
             ((Student)account).updateDailyStreak(new Date());
            completedQuestions.setText(((Student)account).getCompletedQuestions().size() + "");
            dailyStreak.setText(((Student)account).getDailyStreak()+"");
            for (Question question : ((Student) account).getCompletedQuestions()) {
                    addRow(continueTable,
                        question.getTitle() + "\n" + question.getDatePosted(),
                        question.getQuestionTag().getCategory().get(0).toString(),
                        question.getQuestionTag().getLanguage().toString(),
                        question.getQuestionTag().getCourse().get(0).toString(),
                        question.getDifficulty().toString(),
                        question.getRating() + "");
            }
            for (Question question : ((Student) account).getFavoriteQuestions()) {
                addRow(favoritesTable,
                        question.getTitle() + "\n" + question.getDatePosted(),
                        question.getQuestionTag().getCategory().get(0).toString(),
                        question.getQuestionTag().getLanguage().toString(),
                        question.getQuestionTag().getCourse().get(0).toString(),
                        question.getDifficulty().toString(),
                        question.getRating() + "");
            }
            for (Question question : qFacade.getQuestions()) {
                if(((Student)account).getCompletedQuestions().contains(question)) {}
                else {
                    addRow(recommendedTable,
                        question.getTitle() + "\n" + question.getDatePosted(),
                        question.getQuestionTag().getCategory().get(0).toString(),
                        question.getQuestionTag().getLanguage().toString(),
                        question.getQuestionTag().getCourse().get(0).toString(),
                        question.getDifficulty().toString(),
                        question.getRating() + "");
                }
            }
        }
    }

    private void loadProfileImage() {
        if (profileImage == null) {
            return;
        }

        Image image = new Image(getClass().getResourceAsStream("/com/techprep/images/CockyPFP.jpg"));
        profileImage.setImage(image);
        profileImage.setPreserveRatio(true);
        profileImage.setSmooth(true);

        Circle clip = new Circle(31, 31, 31);
        profileImage.setClip(clip);
    }
}
