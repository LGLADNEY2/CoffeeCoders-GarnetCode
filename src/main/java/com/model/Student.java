package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Student extends Account {
    private int dailyStreak;
    private String lastLogin;
    private ArrayList<Question> favoriteQuestions;
    private ArrayList<Question> completedQuestions;
    private ArrayList<QuestionTag> trustedRoles;
    private ArrayList<Question> userQuestions;

    public Student(UUID accountID, String firstName, String lastName, String email, String username, String password, Role role, int dailyStreak, ArrayList<Question> favoriteQuestions, ArrayList<Question> completedQuestions, ArrayList<QuestionTag> trustedRoles, ArrayList<Question> userQuestions){
        super(accountID, username, password, firstName, lastName, email, role);
        this.dailyStreak = 1;
        this.lastLogin = new Date().toString();
        this.favoriteQuestions = favoriteQuestions;
        this.completedQuestions = completedQuestions;
        this.trustedRoles = trustedRoles;
        this.userQuestions = userQuestions;
    }
    public Student(String username, String password, int dailyStreak, ArrayList<Question> favoriteQuestions, ArrayList<Question> completedQuestions, ArrayList<QuestionTag> trustedRoles, ArrayList<Question> userQuestions){
        super(username, password);
        this.dailyStreak = 1;
        this.lastLogin = new Date().toString();
        this.favoriteQuestions = favoriteQuestions;
        this.completedQuestions = completedQuestions;
        this.trustedRoles = trustedRoles;
        this.userQuestions = userQuestions;
    }

    public int getDailyStreak() {return dailyStreak;}
    public String getLastLogin() {return lastLogin;}
    public ArrayList<Question> getFavoriteQuestions() {return favoriteQuestions;}
    public ArrayList<Question> getCompletedQuestions() {return completedQuestions;}
    public ArrayList<QuestionTag> getTrustedRoles() {return trustedRoles;}
    public ArrayList<Question> getUserQuestions() {return userQuestions;}

    public void setDailyStreak(int dailyStreak) {

    }
    public void setLastLogin(String lastLogin) {

    }
    public void setFavoriteQuestions(ArrayList<Question> FavoriteQuestions) {

    }
    public void setCompletedQuestions(ArrayList<Question> completedQuestions) {

    }
    public void setTrustedRoles(ArrayList<QuestionTag> questionTags) {

    }
    public void setUserQuestions(ArrayList<Question> userQuestions) {

    }


    public boolean addFavoriteQuestion(UUID questionID) {
        return true;
    }

    public boolean addCompletedQuestion(UUID Question) {
        return true;
    }

    public boolean addTrustedRole(QuestionTag questionTag) {
        return true;
    }

    public boolean addUserQuestion(Question question, ArrayList<QuestionTag> trustedRoles) {
        return true;
    }

    public boolean removeFavoriteQuestion(UUID Question) {
        return true;
    }

    public boolean removeCompletedQuestion(UUID Question) {
        return true;
    }


    public boolean removeTrustedRole(QuestionTag questionTag) {
        return true;
    }

    public boolean removeUserQuestion(UUID Question) {
        return true;
    }

    public boolean updateDailyStreak(String lastLogin) {
        return true;
    }

    public Segment viewHint() {
        return null;
    }
}