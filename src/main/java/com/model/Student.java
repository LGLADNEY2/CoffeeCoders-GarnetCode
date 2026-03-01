package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class Student extends Account {
    private int dailyStreak;
    private ArrayList<Question> favoriteQuestions;
    private ArrayList<Question> completedQuestions;
    private ArrayList<QuestionTag> trustedRoles;
    private ArrayList<Question> userQuestions;

    public Student(ArrayList<Question> favoriteQuestions) {
        super("", "");
        this.dailyStreak = 0;
        this.favoriteQuestions = favoriteQuestions;
        this.completedQuestions = new ArrayList<>();
        this.trustedRoles = new ArrayList<>();
        this.userQuestions = new ArrayList<>();
    }

    public boolean favoriteQuestion(UUID questionID) {
        return true;
    }

    public boolean addQuestion(Question question, ArrayList<QuestionTag> trustedRoles) {
        return true;
    }

    public Hint viewHint() {
        return null;
    }
}