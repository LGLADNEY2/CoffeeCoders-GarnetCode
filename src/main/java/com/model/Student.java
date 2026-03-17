package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

// Student extends Account with student-specific data like streaks, favorites, and completed questions
public class Student extends Account {
    private int dailyStreak;
    private String lastLogin;
    private ArrayList<Question> favoriteQuestions;
    private ArrayList<Question> completedQuestions;
    private ArrayList<QuestionTag> trustedRoles;
    private ArrayList<Question> userQuestions;

    // basic constructor with just username and password
    public Student(String username, String password) {
        super(username, password);
    }

    // full constructor used by DataLoader to rebuild a student from JSON
    public Student(UUID accountID, String firstName, String lastName, String email, String username, String password, Role role, int dailyStreak, ArrayList<Question> favoriteQuestions, ArrayList<Question> completedQuestions, ArrayList<QuestionTag> trustedRoles, ArrayList<Question> userQuestions){
        super(accountID, username, password, firstName, lastName, email, role);
        this.dailyStreak = 1;
        this.lastLogin = new Date().toString();
        this.favoriteQuestions = favoriteQuestions;
        this.completedQuestions = completedQuestions;
        this.trustedRoles = trustedRoles;
        this.userQuestions = userQuestions;
    }

    // constructor with username/password and all student-specific lists
    public Student(String username, String password, int dailyStreak, ArrayList<Question> favoriteQuestions, ArrayList<Question> completedQuestions, ArrayList<QuestionTag> trustedRoles, ArrayList<Question> userQuestions){
        super(username, password);
        this.dailyStreak = 1;
        this.lastLogin = new Date().toString();
        this.favoriteQuestions = favoriteQuestions;
        this.completedQuestions = completedQuestions;
        this.trustedRoles = trustedRoles;
        this.userQuestions = userQuestions;
    }

    // getters
    public int getDailyStreak() {return dailyStreak;}
    public String getLastLogin() {return lastLogin;}
    public ArrayList<Question> getFavoriteQuestions() {return favoriteQuestions;}
    public ArrayList<Question> getCompletedQuestions() {return completedQuestions;}
    public ArrayList<QuestionTag> getTrustedRoles() {return trustedRoles;}
    public ArrayList<Question> getUserQuestions() {return userQuestions;}

    // setters (stubs)
    public void setDailyStreak(int dailyStreak) {

    }
    public void setLastLogin(String lastLogin) {

    }
    public void setTrustedRoles(ArrayList<QuestionTag> questionTags) {

    }
    public void setUserQuestions(ArrayList<Question> userQuestions) {

    }

    // looks up question by UUID from master list and adds to favorites (skips duplicates)
    public void addFavoriteQuestion(UUID questionID) {
        if (questionID == null) return;
        for (Question q : favoriteQuestions) {
            if (q.getQuestionID().equals(questionID)) return;
        }
        Question question = QuestionList.getInstance().getQuestion(questionID);
        if (question == null) return;
        favoriteQuestions.add(question);
    }

    // looks up question by UUID from master list and adds to completed (skips duplicates)
    public void addCompletedQuestion(UUID questionID) {
        if (questionID == null) return;
        for (Question q : completedQuestions) {
            if (q.getQuestionID().equals(questionID)) return;
        }
        Question question = QuestionList.getInstance().getQuestion(questionID);
        if (question == null) return;
        completedQuestions.add(question);
    }

    // adds a trusted role tag to the student (stub)
    public void addTrustedRole(QuestionTag questionTag) {
        return;
    }

    // adds a user-created question if the student has the right trusted roles (stub)
    public boolean addUserQuestion(Question question, ArrayList<QuestionTag> trustedRoles) {
        return true;
    }

    // finds and removes a question from favorites by UUID
    public void removeFavoriteQuestion(UUID questionID) {
        if (questionID == null) return;
        for (int i = 0; i < favoriteQuestions.size(); i++) {
            if (favoriteQuestions.get(i).getQuestionID().equals(questionID)) {
                favoriteQuestions.remove(i);
                return;
            }
        }
    }

    // finds and removes a question from completed by UUID
    public void removeCompletedQuestion(UUID questionID) {
        if (questionID == null) return;
        for (int i = 0; i < completedQuestions.size(); i++) {
            if (completedQuestions.get(i).getQuestionID().equals(questionID)) {
                completedQuestions.remove(i);
                return;
            }
        }
    }


    // removes a trusted role from the student (stub)
    public boolean removeTrustedRole(QuestionTag questionTag) {
        return true;
    }

    // removes a user-created question by UUID (stub)
    public boolean removeUserQuestion(UUID Question) {
        return true;
    }

    // updates daily streak based on last login date (stub)
    public boolean updateDailyStreak(String lastLogin) {
        return true;
    }

    // returns a hint segment for the current question (stub)
    public Segment viewHint() {
        return null;
    }
}