//Copyright 2026 Christopher Tytone 

package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class QuestionFacade {
    private QuestionList questionList;
    private AccountList accountList;
    private Account currentAccount;
    private Question currentQuestion;

    public QuestionFacade() {
        this.questionList = QuestionList.getInstance();
        this.accountList = AccountList.getInstance();
        this.currentAccount = null;
        this.currentQuestion = null;
    }

    public ArrayList<Question> findQuestion() {
        return new ArrayList<>();
    }

    public ArrayList<Question> findQuestions(String keyword) {
        return new ArrayList<>();
    }

    public ArrayList<Question> findQuestions(QuestionTag tag) {
        return new ArrayList<>();
    }

    public Question getQuestion(UUID id) {
        return null;
    }

    public boolean addQuestion(String title, Difficulty difficulty, QuestionTag tag, ArrayList<Segment> segments, int recTime) {
        return false;
    }

    public boolean addQuestion(String title, Difficulty difficulty, QuestionTag tag, ArrayList<Segment> segments) {
        return false;
    }

    public boolean editQuestion(Difficulty difficulty, QuestionTag tag, ArrayList<Segment> segments) {
        return false;
    }

    public void removeQuestion() {
    }

    public boolean Comment(String text) {
        return false;
    }

    public ArrayList<Question> getFavoriteQuestions() {
        return new ArrayList<>();
    }

    public void giveFeedback(String text, int rating) {
    }

    public void giveFeedback(int rating) {
    }

    public boolean answerQuestion(String code) {
        return false;
    }

    public boolean answerQuestion(String code, int time) {
        return false;
    }

    public boolean submitQuestion(ArrayList<QuestionTag> roles, Question question) {
        return false;
    }

    public boolean submitSolution(Language language, String title, ArrayList<Segment> segments) {
        return false;
    }

    public boolean submitSolution(Language language, String code) {
        return false;
    }

    public void addAccount(String firstName, String lastName, String username, String password, String email) {
        accountList.addAccount(firstName, lastName, username, password, email);
    }

    public void editAccount(String firstName, String lastName, String username, String password, String email) {
        if (currentAccount != null) {
            currentAccount.setFirstName(firstName);
            currentAccount.setLastName(lastName);
            currentAccount.setUsername(username);
            currentAccount.setPassword(password);
            currentAccount.setEmail(email);
        }
        
    }

    public void removeAccount(String username) {
        accountList.deleteAccount(username);
    }

    public boolean login(String user, String password) {
        Account account = accountList.getAccount(user, password);
        if (account != null) {
            currentAccount = account;
            return true;
        } 

        return false;
    }

    public boolean logout() {
        if (currentAccount != null) {
            currentAccount = null;
            return true;
        }
        return false;
    }
}
