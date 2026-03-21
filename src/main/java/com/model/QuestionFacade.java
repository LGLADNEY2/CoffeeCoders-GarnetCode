//Copyright 2026 Christopher Tytone 

package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class QuestionFacade {
    private QuestionList questionList;
    private AccountList accountList;
    private Account currentAccount;
    private Question currentQuestion;
    private Solution currentSolution;

    public QuestionFacade() {
        this.questionList = QuestionList.getInstance();
        this.accountList = AccountList.getInstance();
        this.currentAccount = null;
        this.currentQuestion = null;
        this.currentSolution = null;
    }

    public ArrayList<Question> findQuestion() {
        //what's the express purpose of here?
        return new ArrayList<>();
    }

    public ArrayList<Question> findQuestions(String keyword) {
        return questionList.getQuestions(keyword);
    }

    public ArrayList<Question> findQuestions(QuestionTag tag) {
        return questionList.getQuestions(tag);
    }

    public Question getQuestion(UUID id) {
        currentQuestion = questionList.getQuestion(id);
        return questionList.getQuestion(id);
    }

    //make return question instead of boolean, make new question currentQuestion
    public Question addQuestion(String title, Difficulty difficulty, QuestionTag tag, ArrayList<Segment> segments, int recTime) {
        this.currentQuestion = questionList.getQuestion(questionList.addQuestion(currentAccount.getAccountID(), title, difficulty, tag, segments, new ArrayList<>(), new ArrayList<>(), recTime));
        return currentQuestion;
    }

    public Question addQuestion(String title, Difficulty difficulty, QuestionTag tag, ArrayList<Segment> segments) {
        this.currentQuestion = questionList.getQuestion(questionList.addQuestion(currentAccount.getAccountID(), title, difficulty, tag, segments, new ArrayList<>(), new ArrayList<>(), -1));
        return currentQuestion;
    }

    public boolean editQuestion(Difficulty difficulty, QuestionTag tag, ArrayList<Segment> segments) {
        if(currentQuestion != null) {
            currentQuestion.setDifficulty(difficulty);
            currentQuestion.setQuestionTag(tag);
            currentQuestion.setSegments(segments);
            return true;
        }
        return false;
    }

    public boolean removeQuestion() {
        if (currentQuestion != null) {
            questionList.removeQuestion(currentQuestion.getQuestionID());
            return true;
        }
        return false;
    }

    public boolean Comment(String text) {
        return currentQuestion.addComment(text, currentAccount.getAccountID());
    }

    public ArrayList<Question> getFavoriteQuestions() {
        return new ArrayList<>();
    }

    public void giveFeedback(String text, int rating) {
    }

    public void giveFeedback(int rating) {
    }

    public boolean submitQuestion(ArrayList<QuestionTag> roles, Question question) {
        return false;
    }

    //use this for answers, make currentSolution?
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

    public boolean login(String username, String password) {
        Account account = accountList.getAccount(username, password);
        if (account != null) {
            currentAccount = account;
            return true;
        } 

        return false;
    }
    
    public boolean logout() {
        save();
        if (currentAccount != null) {
            currentAccount = null;
            return true;
        }
        return false;
    }

    public boolean save() {
        return DataWriter.saveAccounts() && DataWriter.saveQuestions();
    }
}
