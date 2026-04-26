//Copyright 2026 Christopher Tytone 

package com.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Provides a simplified interface for account and question workflows.
 * @author Coffee Coders
 */
public class QuestionFacade {
    private QuestionList questionList;
    private AccountList accountList;
    private Account currentAccount;
    private Question currentQuestion;
    private static QuestionFacade facade;

    /**
     * Creates a facade with shared question and account lists.
     */
    private QuestionFacade() {
        this.questionList = QuestionList.getInstance();
        this.accountList = AccountList.getInstance();
        this.currentAccount = null;
        this.currentQuestion = null;
    }

    public static QuestionFacade getInstance() {
        if(facade == null){
            facade = new QuestionFacade();
        }

        return facade;
    }

    /**
     * Finds questions matching a keyword.
     *
     * @param keyword search keyword
     * @return matching questions
     */
    public ArrayList<Question> findQuestions(String keyword) {
        return questionList.getQuestions(keyword);
    }

    /**
     * Finds questions matching a question tag.
     *
     * @param tag question tag filter
     * @return matching questions
     */
    public ArrayList<Question> findQuestions(QuestionTag tag) {
        return questionList.getQuestions(tag);
    }

    /**
     * Gets a question by ID and sets it as the current question.
     *
     * @param id question ID
     * @return matching question, or null if not found
     */
    public Question getQuestion(UUID id) {
        currentQuestion = questionList.getQuestion(id);
        return currentQuestion;
    }

    /**
     * Adds a question with a provided recommended time and sets it as current.
     *
     * @param title question title
     * @param difficulty question difficulty
     * @param tag question tag metadata
     * @param segments question segments
     * @param hints question hints
     * @param recTime recommended solve time
     * @return newly created question
     */
    public Question addQuestion(String title, Difficulty difficulty, QuestionTag tag, ArrayList<Segment> segments, ArrayList<Segment> hints, int recTime) {
        java.util.UUID authorId = (currentAccount != null) ? currentAccount.getAccountID() : java.util.UUID.randomUUID();
        this.currentQuestion = questionList.getQuestion(questionList.addQuestion(authorId, title, difficulty, tag, segments, hints, recTime));
        save();
        return currentQuestion;
    }

    /**
     * Adds a question with default recommended time and sets it as current.
     *
     * @param title question title
     * @param difficulty question difficulty
     * @param tag question tag metadata
     * @param segments question segments
     * @param hints question hints
     * @return newly created question
     */
    public Question addQuestion(String title, Difficulty difficulty, QuestionTag tag, ArrayList<Segment> segments, ArrayList<Segment> hints) {
        java.util.UUID authorId = (currentAccount != null) ? currentAccount.getAccountID() : java.util.UUID.randomUUID();
        this.currentQuestion = questionList.getQuestion(questionList.addQuestion(authorId, title, difficulty, tag, segments, hints, -1));
        save();
        return currentQuestion;
    }

    /**
     * Edits fields on the current question.
     *
     * @param difficulty updated difficulty
     * @param tag updated tag
     * @param segments updated segments
     * @return true if current question exists and is updated, otherwise false
     */
    public boolean editQuestion(Difficulty difficulty, QuestionTag tag, ArrayList<Segment> segments) {
        if (currentQuestion != null) {
            currentQuestion.setDifficulty(difficulty);
            currentQuestion.setQuestionTag(tag);
            currentQuestion.setSegments(segments);
            save();
            return true;
        }
        return false;
    }

    /**
     * Removes the current question.
     *
     * @return true if a question was removed, otherwise false
     */
    public boolean removeQuestion() {
        if (currentQuestion != null) {
            questionList.removeQuestion(currentQuestion.getQuestionID());
            save();
            return true;
        }
        return false;
    }

    /**
     * Adds a comment to the current question from the current account.
     *
     * @param text comment text
     * @return true if comment is added, otherwise false
     */
    public boolean Comment(String text) {
        return currentQuestion.addComment(text, currentAccount.getAccountID());
    }

    /**
     * Gets favorite questions for the current student account.
     *
     * @return favorite questions, or null for non-student accounts
     */
    public ArrayList<Question> getFavoriteQuestions() {
        if (currentAccount.getRole() == Role.STUDENT) {
            Student student = (Student) currentAccount;
            return student.getFavoriteQuestions();
        }
        return null;
    }

    /**
     * Submits a rating for the current question.
     *
     * @param rating rating value
     */
    public void giveFeedback(int rating) {
        currentQuestion.addRating(rating);
    }


    /**
     * Submits a question to student trusted roles.
     *
     * @param roles target role tags
     * @param question question to submit
     * @return true if submission succeeds, otherwise false
     */
    public boolean submitQuestion(ArrayList<QuestionTag> roles, Question question) {
        if (currentAccount.getRole() == Role.STUDENT) {
            Student student = (Student)currentAccount;
            return student.addUserQuestion(question, roles);
        }
        return false;
    }

    /**
     * Submits a solution for the current question.
     *
     * @param language solution language
     * @param title solution title
     * @param segments solution content segments
     * @return true if submission succeeds, otherwise false
     */
    public boolean submitSolution(Language language, String title, ArrayList<Segment> segments) {
        if(currentQuestion.addSolution(currentAccount.getAccountID(), title, language, segments)) {
            save();
            return true;
        }
        return false;
    }

    /**
     * Adds a student account.
     *
     * @param firstName first name
     * @param lastName last name
     * @param username username
     * @param password password
     * @param email email address
     * @return true if account is added, otherwise false
     */
    public boolean addAccount(String firstName, String lastName, String email, String username, String password) {
        if(password == null || password.length()<7 || password.contains(" ") || email.contains(" ") || username.contains(" ")) {
            return false;
        }
        if(accountList.addAccount(firstName,lastName, email, username, password)) {
            save();
            return true;
        }
        return false;
    }

    /**
     * Adds an account with a provided role.
     *
     * @param firstName first name
     * @param lastName last name
     * @param username username
     * @param password password
     * @param email email address
     * @param role account role
     * @return true if account is added, otherwise false
     */
    public boolean addAccount(String firstName, String lastName, String email, String username, String password, Role role) {
        if(password == null || password.length()<7 || password.contains(" ") || email.contains(" ") || username.contains(" ") || role == null) {
            return false;
        }
        if(accountList.addAccount(firstName, lastName, email, username, password, role)) {
            save();
            return true;
        }
        return false;
    }

    /**
     * Edits profile fields for the current account.
     *
     * @param firstName updated first name
     * @param lastName updated last name
     * @param username updated username
     * @param password updated password
     * @param email updated email
     */
    public void editAccount(String firstName, String lastName, String username, String password, String email) {
        if (currentAccount != null) {
            currentAccount.setFirstName(firstName);
            currentAccount.setLastName(lastName);
            currentAccount.setUsername(username);
            currentAccount.setPassword(password);
            currentAccount.setEmail(email);
            save();
        }
    }

    /**
     * Removes an account by account ID.
     *
     * @param accountID account ID to remove
     */
    public void removeAccount(UUID accountID) {
        accountList.deleteAccount(accountID);
    }


    /**
     * Updates the daily streak for the current student account.
     */
    public void updateDailyStreak() {
        if (currentAccount != null && currentAccount.getRole() == Role.STUDENT) {
            Student student = (Student) currentAccount;
            student.updateDailyStreak(new java.util.Date());
            save();
        }
    }

    /**
     * Gets the current student's daily streak.
     *
     * @return current daily streak, or 0 if unavailable
     */
    public int getDailyStreak() {
        if (currentAccount != null && currentAccount.getRole() == Role.STUDENT) {
            Student student = (Student) currentAccount;
            return student.getDailyStreak();
        }
        return 0;
    }

    /**
     * Exports the current question and related details to a text file.
     *
     * @param filePath output file path
     * @return true if export succeeds, otherwise false
     */
    public boolean exportCurrentQuestionToFile(String filePath) {
        if (currentQuestion == null) return false;
        try (java.io.PrintWriter writer = new java.io.PrintWriter(filePath)) {
            writer.println("Question: " + currentQuestion.getTitle());
            writer.println("Difficulty: " + currentQuestion.getDifficulty());
            writer.println("Recommended Time: " + currentQuestion.getRecommendedTime() + " min");
            writer.println("Segments:");
            for (Segment seg : currentQuestion.getSegments()) {
                writer.println("  - " + seg.getTitle() + ": " + seg.getDesc());
            }
            writer.println("Hints:");
            for (Segment hint : currentQuestion.getHints()) {
                writer.println("  - " + hint.getTitle() + ": " + hint.getDesc());
            }
            writer.println("Solutions:");
            for (Solution sol : currentQuestion.getSolutions()) {
                writer.println("  - Title: " + sol.getTitle());
                writer.println("    Language: " + sol.getLanguage());
                for (Segment seg : sol.getSegments()) {
                    writer.println("    Segment: " + seg.getTitle() + " - " + seg.getDesc());
                }
            }
            writer.println("Comments:");
            for (Comment comment : currentQuestion.getComments()) {
                writer.println("  - [" + comment.getDatePosted() + "] " + comment.getAccountID() + ": " + comment.getText());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Logs in with the provided credentials.
     *
     * @param username username
     * @param password password
     * @return true if login succeeds, otherwise false
     */
    public boolean login(String username, String password) {
        Account account = accountList.getAccount(username, password);
        if (account != null) {
            currentAccount = account;
            return true;
        }

        return false;
    }

    /**
     * Logs out the current account after saving data.
     *
     * @return true if an account was logged out, otherwise false
     */
    public boolean logout() {
        save();
        if (currentAccount != null) {
            currentAccount = null;
            return true;
        }
        return false;
    }

    public Account getCurrentAccount(){
        return currentAccount;
    }

    /**
     * Returns the currently selected question, if any.
     *
     * @return current question or null
     */
    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public ArrayList<Question> getQuestions() {
        return questionList.getQuestions();
    }

    /**
     * Saves question and account data.
     *
     * @return true if both saves succeed, otherwise false
     */
    public boolean save() {
        return questionList.save() && accountList.saveAccount();
    }
}