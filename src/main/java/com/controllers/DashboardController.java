package com.controllers;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DashboardController implements Initializable{
    private QuestionFacade qFacade;
    private Account account;
    @FXML private TableView<ObservableList<String>> continueTable;
    @FXML private TableColumn<ObservableList<String>, String> colTitleDate;
    @FXML private TableColumn<ObservableList<String>, String> colCategory;
    @FXML private TableColumn<ObservableList<String>, String> colLanguage;
    @FXML private TableColumn<ObservableList<String>, String> colClass;
    @FXML private TableColumn<ObservableList<String>, String> colDifficulty;
    @FXML private TableColumn<ObservableList<String>, String> colRating;


    @FXML
    private void goToCreate(ActionEvent event) throws IOException {
        App.setRoot("create_question");
    }

    @FXML
    private void goToQuestions(ActionEvent event) throws IOException {
        App.setRoot("question_list");
    }

    private void addRow(TableView<ObservableList<String>> table, String titleDate, String category, String language, String course, String difficulty, String rating) {
        ObservableList<String> row = FXCollections.observableArrayList(
            titleDate, category, language, course, difficulty, rating);
        table.getItems().add(row);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        qFacade = QuestionFacade.getInstance();

        Account account = qFacade.getCurrentAccount();

        System.out.println("account is " + account);
      //  qFacade.setCurrentAccount(account);
        colTitleDate.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().get(0)));
        colCategory.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().get(1)));
        colLanguage.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().get(2)));
        colClass.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().get(3)));
        colDifficulty.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().get(4)));
        colRating.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().get(5)));
        continueTable.setItems(FXCollections.observableArrayList());
        if(account.getRole() == Role.STUDENT) {
            for(Question question: ((Student)account).getCompletedQuestions()) {
                addRow(continueTable, (question.getTitle() + " " + question.getDatePosted()), question.getQuestionTag().getCategory().get(0).toString(), question.getQuestionTag().getCourse().get(0).toString(), question.getQuestionTag().getCourse().get(0).toString(), question.getDifficulty().toString(), question.getRating() + "");
            }
        }
    }
}
