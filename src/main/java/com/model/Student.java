package com.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Represents a student account with streak, favorites, completed questions, and trusted role data.
 * @author Coffee Coders
 */
public class Student extends Account {
    private int dailyStreak;
    private String lastLogin;
    private ArrayList<Question> favoriteQuestions;
    private ArrayList<Question> completedQuestions;
    private ArrayList<QuestionTag> trustedRoles;
    private ArrayList<Question> userQuestions;

    /**
     * Creates a student account using username and password.
     *
     * @param username student username
     * @param password student password
     */
    public Student(String username, String password) {
        super(username, password);
        this.setRole(Role.STUDENT);
    }

    /**
     * Creates a student account with all fields, typically from loaded data.
     *
     * @param accountID account ID
     * @param firstName first name
     * @param lastName last name
     * @param email email address
     * @param username username
     * @param password password
     * @param role account role
     * @param dailyStreak current daily streak
     * @param favoriteQuestions favorite question list
     * @param completedQuestions completed question list
     * @param trustedRoles trusted role tags
     * @param userQuestions user-created questions
     */
    public Student(UUID accountID, String firstName, String lastName, String email, String username, String password, Role role, int dailyStreak, ArrayList<Question> favoriteQuestions, ArrayList<Question> completedQuestions, ArrayList<QuestionTag> trustedRoles, ArrayList<Question> userQuestions){
        super(accountID, username, password, firstName, lastName, email, role);
        this.dailyStreak = dailyStreak;
        DateFormat dp = new SimpleDateFormat("MM/dd/yyyy kk:mm");
        this.lastLogin = dp.format(new Date());
        this.favoriteQuestions = favoriteQuestions;
        this.completedQuestions = completedQuestions;
        this.trustedRoles = trustedRoles;
        this.userQuestions = userQuestions;
    }

    /**
     * Creates a new student account with generated account ID.
     *
     * @param firstName first name
     * @param lastName last name
     * @param email email address
     * @param username username
     * @param password password
     * @param role account role
     * @param dailyStreak current daily streak
     * @param favoriteQuestions favorite question list
     * @param completedQuestions completed question list
     * @param trustedRoles trusted role tags
     * @param userQuestions user-created questions
     */
    public Student(String firstName, String lastName, String email, String username, String password, Role role, int dailyStreak, ArrayList<Question> favoriteQuestions, ArrayList<Question> completedQuestions, ArrayList<QuestionTag> trustedRoles, ArrayList<Question> userQuestions){
        super(UUID.randomUUID(), username, password, firstName, lastName, email, role);
        this.dailyStreak = 1;
        DateFormat dp = new SimpleDateFormat("MM/dd/yyyy kk:mm");
        this.lastLogin = dp.format(new Date());
        this.favoriteQuestions = favoriteQuestions;
        this.completedQuestions = completedQuestions;
        this.trustedRoles = trustedRoles;
        this.userQuestions = userQuestions;
    }

    /**
     * Returns the student's daily streak.
     *
     * @return daily streak
     */
    public int getDailyStreak() {return dailyStreak;}

    /**
     * Returns the student's last login date.
     *
     * @return last login date
     */
    public String getLastLogin() {return lastLogin;}

    /**
     * Returns the student's favorite questions.
     *
     * @return favorite question list
     */
    public ArrayList<Question> getFavoriteQuestions() {return favoriteQuestions;}

    /**
     * Returns the student's completed questions.
     *
     * @return completed question list
     */
    public ArrayList<Question> getCompletedQuestions() {return completedQuestions;}

    /**
     * Returns the student's trusted role tags.
     *
     * @return trusted role list
     */
    public ArrayList<QuestionTag> getTrustedRoles() {return trustedRoles;}

    /**
     * Returns questions created by the student.
     *
     * @return user-created question list
     */
    public ArrayList<Question> getUserQuestions() {return userQuestions;}

    /**
     * Sets the student's daily streak.
     *
     * @param dailyStreak new daily streak
     */
    public void setDailyStreak(int dailyStreak) {
        this.dailyStreak = dailyStreak;
    }

    /**
     * Sets the student's last login date.
     *
     * @param lastLogin new login date
     */
    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * Sets the student's trusted role tags.
     *
     * @param questionTags new trusted role list
     */
    public void setTrustedRoles(ArrayList<QuestionTag> questionTags) {
        this.trustedRoles = questionTags;
    }

    /**
     * Sets the student's user-created questions.
     *
     * @param userQuestions new question list
     */
    public void setUserQuestions(ArrayList<Question> userQuestions) {
        this.userQuestions = userQuestions;
    }

    /**
     * Adds a question to favorites by question ID.
     *
     * @param questionID question ID to add
     */
    public void addFavoriteQuestion(UUID questionID) {
        if (questionID == null) return;
        for (Question q : favoriteQuestions) {
            if (q.getQuestionID().equals(questionID)) return;
        }
        Question question = QuestionList.getInstance().getQuestion(questionID);
        if (question == null) return;
        favoriteQuestions.add(question);
    }

    /**
     * Adds a question to completed questions by question ID.
     *
     * @param questionID question ID to add
     */
    public void addCompletedQuestion(UUID questionID) {
        if (questionID == null) return;
        for (Question q : completedQuestions) {
            if (q.getQuestionID().equals(questionID)) return;
        }
        Question question = QuestionList.getInstance().getQuestion(questionID);
        if (question == null) return;
        completedQuestions.add(question);
    }

    /**
     * Adds a trusted role tag to this student.
     *
     * @param questionTag trusted role tag to add
     */
    public void addTrustedRole(QuestionTag questionTag) {
        trustedRoles.add(questionTag);
    }

    /**
     * Adds a user-created question if it matches one of the trusted roles.
     *
     * @param question question to add
     * @param trustedRoles trusted role tags to validate against
     * @return true if question is added, otherwise false
     */
    public boolean addUserQuestion(Question question, ArrayList<QuestionTag> trustedRoles) {
        for(QuestionTag tag: trustedRoles) {
            if(question.getQuestionTag().getCategory().contains(tag.getCategory().get(0)) &&
                question.getQuestionTag().getCourse().contains(tag.getCourse().get(0)) &&
                question.getQuestionTag().getLanguage().contains(tag.getLanguage().get(0))) {
                return userQuestions.add(question);
            }
        }
        return false;
    }

    /**
     * Removes a favorite question by question ID.
     *
     * @param questionID question ID to remove
     */
    public void removeFavoriteQuestion(UUID questionID) {
        if (questionID == null) return;
        for (int i = 0; i < favoriteQuestions.size(); i++) {
            if (favoriteQuestions.get(i).getQuestionID().equals(questionID)) {
                favoriteQuestions.remove(i);
                return;
            }
        }
    }

    /**
     * Removes a completed question by question ID.
     *
     * @param questionID question ID to remove
     */
    public void removeCompletedQuestion(UUID questionID) {
        if (questionID == null) return;
        for (int i = 0; i < completedQuestions.size(); i++) {
            if (completedQuestions.get(i).getQuestionID().equals(questionID)) {
                completedQuestions.remove(i);
                return;
            }
        }
    }


    /**
     * Removes a trusted role tag from this student.
     *
     * @param questionTag trusted role tag to remove
     * @return true if removed, otherwise false
     */
    public boolean removeTrustedRole(QuestionTag questionTag) {
        for(QuestionTag tag: trustedRoles) {
            if(tag.equals(questionTag))
                return trustedRoles.remove(tag);
        }
        return false;
    }

    /**
     * Removes a user-created question by question ID.
     *
     * @param Question question ID to remove
     * @return true if removed, otherwise false
     */
    public boolean removeUserQuestion(UUID Question) {
        for(Question question: userQuestions) {
            if(question.getQuestionID().equals(Question))
               return userQuestions.remove(question); 
        }
        return false;
    }

    /**
     * Updates the daily streak
     *
     * @param newLogin new login date
     */
    public void updateDailyStreak() {
        if (lastLogin == null || lastLogin.isEmpty()) {
            DateFormat dp = new SimpleDateFormat("MM/dd/yyyy kk:mm");
            lastLogin = dp.format(new Date());
            dailyStreak = 1;
            return;
        }
        try {
            // Parse the stored lastLogin date
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy kk:mm");
            Date lastDate = sdf.parse(lastLogin);
        
            // Get current date
            Date now = new Date();
        
            // Reset both dates to midnight for day comparison
            Calendar lastCal = Calendar.getInstance();
            lastCal.setTime(lastDate);
        
            Calendar nowCal = Calendar.getInstance();
            nowCal.setTime(now);

        
            // Calculate difference in days
            long diffMillis = nowCal.getTimeInMillis() - lastCal.getTimeInMillis();
            int daysDiff = (int) (diffMillis / (24 * 60 * 60 * 1000));
        
            if (daysDiff == 1) {
                dailyStreak++;
            } else if (daysDiff > 1) {
                dailyStreak = 1;
            }
        
            // Update lastLogin to current date and time
            DateFormat dp = new SimpleDateFormat("MM/dd/yyyy kk:mm");
            lastLogin = dp.format(new Date());
        
        } catch (java.text.ParseException e) {
            // If parsing fails, reset everything
            System.err.println("Failed to parse lastLogin: " + lastLogin);
            DateFormat dp = new SimpleDateFormat("MM/dd/yyyy kk:mm");
            lastLogin = dp.format(new Date());
            dailyStreak = 1;
        }
    }

    /**
     * Returns a hint segment for the current question.
     *
     * @return hint segment, or null when unavailable
     */
    public Segment viewHint() {
        return null;
    }
}