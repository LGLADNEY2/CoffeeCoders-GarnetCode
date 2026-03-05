//Copyright 2026 Christopher Tytone 

package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class QuestionFacade {
    private QuestionList questionList;
    private AccountList userList;
    private Account currentUser;
    private Question currentQuestion;

    public QuestionFacade() {
        this.questionList = QuestionList.getInstance();
        this.userList = AccountList.getInstance();
        this.currentUser = null;
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

    public boolean addQuestion(String title, Difficulty difficulty, QuestionTag tag, String desc, int recTime) {
        return false;
    }

    public boolean addQuestion(ArrayList<QuestionSegment> segmentOrder, ArrayList<String> segments) {
        return false;
    }

    public boolean editQuestion(ArrayList<QuestionSegment> segmentOrder, ArrayList<String> segments) {
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

    public boolean submitSolution(Language language, String title, ArrayList<SolutionSegment> segmentOrder, ArrayList<String> segments) {
        return false;
    }

    public boolean submitSolution(Language language, String code) {
        return false;
    }

    public void addUser() {
    }

    public void editUser() {
    }

    public void removeUser() {
    }

    public boolean login(String user, String password) {
        return false;
    }

    public boolean login() {
        return false;
    }

    public boolean logout() {
        return false;
    }
}
