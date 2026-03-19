package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class QuestionList {
    private static QuestionList questionList;
    private ArrayList<Question> questions;

    private QuestionList() {
        questions = DataLoader.getQuestions();
    }

    public static QuestionList getInstance() {
        if (questionList == null)
            questionList = new QuestionList();
        return questionList;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    // turn into helper methods or make one method if possible?
    // returns questions that match the given tag
    public ArrayList<Question> getQuestions(QuestionTag questionTag) {
        ArrayList<Question> filtered = new ArrayList<>();
        for (Question question : questions) {
            QuestionTag qTag = question.getQuestionTag();
            if (qTag == null) continue;

            boolean match = false;

            // Check for overlapping categories
            if (questionTag.getCategory() != null && qTag.getCategory() != null) {
                for (Category cat : questionTag.getCategory()) {
                    if (qTag.getCategory().contains(cat)) {
                        match = true;
                        break;
                    }
                }
            }

            // Check for overlapping languages
            if (!match && questionTag.getLanguage() != null && qTag.getLanguage() != null) {
                for (Language lang : questionTag.getLanguage()) {
                    if (qTag.getLanguage().contains(lang)) {
                        match = true;
                        break;
                    }
                }
            }

            // Check for overlapping courses
            if (!match && questionTag.getCourse() != null && qTag.getCourse() != null) {
                for (Course course : questionTag.getCourse()) {
                    if (qTag.getCourse().contains(course)) {
                        match = true;
                        break;
                    }
                }
            }

            if (match) {
                filtered.add(question);
            }
        }
        return filtered;
    }

    // creates a new question and adds it to the list
    public boolean addQuestion(UUID questionID, UUID authorID, String title, int rating, String datePosted, int recommendedTime, Difficulty difficulty, ArrayList<Segment> segments, QuestionTag tag, ArrayList<Segment> hints, ArrayList<Solution> solutions, ArrayList<Comment> comments) {
        if (title == null || title.isEmpty() || authorID == null) {
            return false;
        }
        Question question = new Question(questionID, authorID, title, rating, datePosted,
                recommendedTime, difficulty, segments, tag, hints, solutions, comments);
        questions.add(question);
        return true;
    }

    // removes a question by its id
    public boolean removeQuestion(UUID questionID) {
        if (questionID == null) return false;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getQuestionID().equals(questionID)) {
                questions.remove(i);
                return true;
            }
        }
        return false;
    }

    //move to account class
    // returns the completed questions for a given account
    public ArrayList<Question> viewCompleted(UUID accountID) {
        AccountList accountList = AccountList.getInstance();
        for (Account account : accountList.getAccounts()) {
            if (account.getAccountID().equals(accountID) && account instanceof Student) {
                return ((Student) account).getCompletedQuestions();
            }
        }
        return new ArrayList<>();
    }

    // gets a question by its id
    public Question getQuestion(UUID questionID) {
        if (questionID == null) return null;
        for (Question question : questions) {
            if (question.getQuestionID().equals(questionID)) {
                return question;
            }
        }
        return null;
    }

    // saves all questions to file
    public boolean save() {
        return DataWriter.saveQuestions();
    }
}