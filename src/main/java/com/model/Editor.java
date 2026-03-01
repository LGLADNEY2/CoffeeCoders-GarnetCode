package com.model;

import java.util.UUID;

public class Editor {
    private boolean admin;

    public Editor() {
        this.admin = false;
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