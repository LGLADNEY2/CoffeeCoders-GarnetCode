//Copyright 2026 Christopher Tytone 

package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class QuestionFacade {
    private QuestionList questionList;
    private AccountList accountList;
    private Account currentAccount;
    private Question currentQuestion;

    public QuestionFacade() {
        this.questionList = QuestionList.getInstance();
        this.accountList = AccountList.getInstance();
        this.currentAccount = null;
        this.currentQuestion = null;
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
        return currentQuestion;
    }

    //make return question instead of boolean, make new question currentQuestion
    public Question addQuestion(String title, Difficulty difficulty, QuestionTag tag, ArrayList<Segment> segments, ArrayList<Segment> hints, int recTime) {
        this.currentQuestion = questionList.getQuestion(questionList.addQuestion(currentAccount.getAccountID(), title, difficulty, tag, segments, hints, recTime));
        return currentQuestion;
    }

    public Question addQuestion(String title, Difficulty difficulty, QuestionTag tag, ArrayList<Segment> segments, ArrayList<Segment> hints) {
        this.currentQuestion = questionList.getQuestion(questionList.addQuestion(currentAccount.getAccountID(), title, difficulty, tag, segments, hints, -1));
        return currentQuestion;
    }

    public boolean editQuestion(Difficulty difficulty, QuestionTag tag, ArrayList<Segment> segments) {
        if (currentQuestion != null) {
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

    //gets a list of favorite questions linked to an account
    public ArrayList<Question> getFavoriteQuestions() {
        if (currentAccount.getRole() == Role.STUDENT) {
            Student student = (Student) currentAccount;
            return student.getFavoriteQuestions();
        }
        return null;
    }

    //figure out where this goes

    public void giveFeedback(int rating) {
        currentQuestion.addRating(rating);
    }


    public boolean submitQuestion(ArrayList<QuestionTag> roles, Question question) {
        if (currentAccount.getRole() == Role.STUDENT) {
            Student student = (Student) currentAccount;
            return student.addUserQuestion(question, roles);
        }
        return false;
    }

    public boolean submitSolution(Language language, String title, ArrayList<Segment> segments) {
        return currentQuestion.addSolution(currentAccount.getAccountID(), title, language, segments);
    }

    public boolean addAccount(String firstName, String lastName, String username, String password, String email) {
        return accountList.addAccount(firstName, lastName, email, username, password);
    }

    public boolean addAccount(String firstName, String lastName, String username, String password, String email, Role role) {
        return accountList.addAccount(firstName, lastName, email, username, password, role);
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


    // Updates the daily streak for the current student (using today's date)
    public void updateDailyStreak() {
        if (currentAccount != null && currentAccount.getRole() == Role.STUDENT) {
            Student student = (Student) currentAccount;
            student.updateDailyStreak(new java.util.Date());
        }
    }

    // Gets the daily streak for the current student
    public int getDailyStreak() {
        if (currentAccount != null && currentAccount.getRole() == Role.STUDENT) {
            Student student = (Student) currentAccount;
            return student.getDailyStreak();
        }
        return 0;
    }

    // Exports the current question to a formatted text file
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