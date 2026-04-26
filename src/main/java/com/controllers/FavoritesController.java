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
 * Controller for the favorites view. Displays only the current student's
 * favorite questions while keeping dashboard-like profile details.
 */
public class FavoritesController implements Initializable {
    private QuestionFacade qFacade;
    private Account account;

    @FXML private Label completedQuestions;
    @FXML private Label dailyStreak;
    @FXML private TableView<Question> favoritesTable;
    @FXML private TableColumn<Question, String> favTitleDate;
    @FXML private TableColumn<Question, String> favCategory;
    @FXML private TableColumn<Question, String> favLanguage;
    @FXML private TableColumn<Question, String> favClass;
    @FXML private TableColumn<Question, String> favDifficulty;
    @FXML private TableColumn<Question, String> favRating;
    @FXML private ImageView profileImage;

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

    @FXML
    private void openSettings(ActionEvent event) throws IOException {
        App.setRoot("settings");
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        qFacade.logout();
        App.setRoot("home");
    }

    private void configureTableColumns() {
        favTitleDate.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getTitle() + "\n" +
                    cellData.getValue().formatDate(cellData.getValue().getDatePosted())));

        favCategory.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getQuestionTag().getCategory().get(0).toString()));

        favLanguage.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getQuestionTag().getLanguage().toString()));

        favClass.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getQuestionTag().getCourse().get(0).toString()));

        favDifficulty.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getDifficulty().toString()));

        favRating.setCellValueFactory(cellData ->
            new SimpleStringProperty(String.valueOf(cellData.getValue().getRating())));

        favTitleDate.setPrefWidth(320);
        favCategory.setPrefWidth(140);
        favLanguage.setPrefWidth(120);
        favClass.setPrefWidth(120);
        favDifficulty.setPrefWidth(140);
        favRating.setPrefWidth(90);

        favTitleDate.setMinWidth(220);
        favCategory.setMinWidth(110);
        favLanguage.setMinWidth(95);
        favClass.setMinWidth(95);
        favDifficulty.setMinWidth(110);
        favRating.setMinWidth(70);
    }

    private void setupTableClickHandler() {
        favoritesTable.setRowFactory(tv -> {
            TableRow<Question> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Question selectedQuestion = row.getItem();
                    if (selectedQuestion != null) {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        qFacade = QuestionFacade.getInstance();
        account = qFacade.getCurrentAccount();

        loadProfileImage();
        configureTableColumns();
        setupTableClickHandler();
        favoritesTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        if (account != null && account.getRole() == Role.STUDENT) {
            Student student = (Student) account;
            student.updateDailyStreak(new Date());
            completedQuestions.setText(String.valueOf(student.getCompletedQuestions().size()));
            dailyStreak.setText(String.valueOf(student.getDailyStreak()));
            favoritesTable.setItems(FXCollections.observableArrayList(student.getFavoriteQuestions()));
        } else {
            completedQuestions.setText("0");
            dailyStreak.setText("0");
            favoritesTable.setItems(FXCollections.observableArrayList());
        }
    }

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
