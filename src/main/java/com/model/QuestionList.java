package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class QuestionList{
    private static QuestionList questionList;
    private ArrayList<Question> questions;

    private QuestionList() {
        questionList = DataLoader.getQuestions();
    }

    public static QuestionList getInstance() {
        if(questionList == null)
            questionList = new QuestionList();
        return questionList;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public ArrayList<Question> getQuestions(QuestionTag questionTag) {
        return null;
    }

    public boolean addQuestion(UUID authorID, String title, int recommendedTime, Difficulty difficulty, ArrayList<Segment> segments, QuestionTag tag, ArrayList<Segment> hints, ArrayList<Solution> solutions) {
        return true;
    }

    public boolean removeQuestion(UUID questionID) {
        return true;
    }

    public ArrayList<Question> viewCompleted(UUID accountID) {
        return null;
    }
    public boolean save() {
        return true;
    }
}