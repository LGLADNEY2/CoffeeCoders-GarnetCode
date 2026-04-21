package com.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Manages the collection of questions and question-related lookups.
 * @author Coffee Coders
 */
public class QuestionList {
    private static QuestionList questionList;
    private ArrayList<Question> questions;

    /**
     * Creates the question list and loads questions from the data source.
     */
    private QuestionList() {
        questions = DataLoader.getQuestions();
    }

    /**
     * Returns the singleton instance of QuestionList.
     *
     * @return single QuestionList instance
     */
    public static QuestionList getInstance() {
        if (questionList == null)
            questionList = new QuestionList();
        return questionList;
    }

    /**
     * Returns all questions loaded from the data source.
     *
     * @return list of questions
     */
    public ArrayList<Question> getQuestions() {
        // Return the in-memory list managed by this singleton instance.
        return new ArrayList<>(questions);
    }

    /**
     * Returns questions that match the provided tag criteria.
     *
     * @param questionTag tag criteria used for filtering
     * @return filtered list of matching questions
     */
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

    /**
     * Returns questions whose title matches the provided keyword.
     *
     * @param keyWord keyword used for title matching
     * @return filtered list of matching questions
     */
    public ArrayList<Question> getQuestions(String keyWord) {
        ArrayList<Question> filtered = new ArrayList<>();
        for (Question question : questions) {
            if(question.getTitle().equalsIgnoreCase(keyWord))
                filtered.add(question);
        }
        return filtered;
    }

    /**
     * Creates a new question and adds it to the list.
     *
     * @param authorID author account ID
     * @param title question title
     * @param difficulty question difficulty
     * @param tag question tag metadata
     * @param segments question segments
     * @param hints question hints
     * @param recommendedTime recommended solve time
     * @return ID of the created question, or null if creation fails
     */
    public UUID addQuestion(UUID authorID, String title, Difficulty difficulty, QuestionTag tag, ArrayList<Segment> segments, ArrayList<Segment> hints, int recommendedTime) {
        if (title == null || title.isEmpty() || authorID == null) {
            return null;
        }
        Question question = new Question(authorID, title, recommendedTime, difficulty, segments, tag, hints);
        questions.add(question);
        return question.getQuestionID(); //make return new question uuid
    }

    /**
     * Removes a question by its ID.
     *
     * @param questionID ID of the question to remove
     * @return true if a question is removed, otherwise false
     */
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

    /**
     * Returns completed questions for a specific account.
     *
     * @param accountID account ID to inspect
     * @return list of completed questions for the account
     */
    public ArrayList<Question> viewCompleted(UUID accountID) {
        AccountList accountList = AccountList.getInstance();
        for (Account account : accountList.getAccounts()) {
            if (account.getAccountID().equals(accountID) && account instanceof Student) {
                return ((Student) account).getCompletedQuestions();
            }
        }
        return new ArrayList<>();
    }

    /**
     * Gets a question by its ID.
     *
     * @param questionID question ID
     * @return matching question, or null if not found
     */
    public Question getQuestion(UUID questionID) {
        if (questionID == null) return null;
        for (Question question : questions) {
            if (question.getQuestionID().equals(questionID)) {
                return question;
            }
        }
        return null;
    }

    /**
     * Saves all questions to the data file.
     *
     * @return true if save succeeds, otherwise false
     */
    public boolean save() {
        return DataWriter.saveQuestions();
    }
}