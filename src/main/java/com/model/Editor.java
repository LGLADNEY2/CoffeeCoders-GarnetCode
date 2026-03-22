package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class Editor extends Student{
    private boolean admin;

    public Editor(String username, String password) {
        super(username, password);
        this.admin = true;
    }

    public Editor(String firstName, String lastName, String email, String username, String password, Role role, int dailyStreak, ArrayList<Question> favoriteQuestions, ArrayList<Question> completedQuestions, ArrayList<QuestionTag> trustedRoles, ArrayList<Question> userQuestions) {
        super(UUID.randomUUID(), firstName, lastName, email, username, password, role, dailyStreak, favoriteQuestions, completedQuestions, trustedRoles, userQuestions);
        this.admin = false;
    }
    
    public Editor(UUID accountID, String firstName, String lastName, String email, String username, String password, Role role, int dailyStreak, ArrayList<Question> favoriteQuestions, ArrayList<Question> completedQuestions, ArrayList<QuestionTag> trustedRoles, ArrayList<Question> userQuestions, boolean admin) {
        super(accountID, firstName, lastName, email, username, password, role, dailyStreak, favoriteQuestions, completedQuestions, trustedRoles, userQuestions);
        this.admin = admin;
    }

    public boolean getAdmin() {return admin;}

    public void setAdmin(boolean admin) {
        
    }

    public boolean createQuestion(Question question) {
        return true;
    }

    public boolean editQuestion(Question question) {
        return true;
    }

    public boolean createSolution(Question question, Solution solution) {
        return true;
    }

    public boolean approveQuestion(boolean admin, Question question) {
        return true;
    }

    public boolean approveSolution(boolean admin, Solution solution) {
        return true;
    }

    public boolean removeQuestion(boolean admin, Question question) {
        return true;
    }

    public boolean removeSolution(boolean admin, Solution solution) {
        return true;
    }

    public boolean addTrustedRole(boolean admin, UUID accountID, QuestionTag questionTag) {
        return true;
    }

    public boolean addAdmin(UUID accountID) {
        return true;
    }

    public boolean removeAdmin(UUID accountID) {
        return true;
    }
}