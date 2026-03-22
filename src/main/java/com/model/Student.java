package com.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

// Student extends Account with student-specific data like streaks, favorites, and completed questions
public class Student extends Account {
    private int dailyStreak;
    private Date lastLogin;
    private ArrayList<Question> favoriteQuestions;
    private ArrayList<Question> completedQuestions;
    private ArrayList<QuestionTag> trustedRoles;
    private ArrayList<Question> userQuestions;

    // basic constructor with just username and password
    public Student(String username, String password) {
        super(username, password);
        this.setRole(Role.STUDENT);
    }

    // full constructor used by DataLoader to rebuild a student from JSON
    public Student(UUID accountID, String firstName, String lastName, String email, String username, String password, Role role, int dailyStreak, ArrayList<Question> favoriteQuestions, ArrayList<Question> completedQuestions, ArrayList<QuestionTag> trustedRoles, ArrayList<Question> userQuestions){
        super(accountID, username, password, firstName, lastName, email, role);
        this.dailyStreak = dailyStreak;
        this.lastLogin = new Date();
        this.favoriteQuestions = favoriteQuestions;
        this.completedQuestions = completedQuestions;
        this.trustedRoles = trustedRoles;
        this.userQuestions = userQuestions;
    }

    // constructor with username/password and all student-specific lists
    public Student(String firstName, String lastName, String email, String username, String password, Role role, int dailyStreak, ArrayList<Question> favoriteQuestions, ArrayList<Question> completedQuestions, ArrayList<QuestionTag> trustedRoles, ArrayList<Question> userQuestions){
        super(UUID.randomUUID(), username, password, firstName, lastName, email, role);
        this.dailyStreak = 1;
        this.lastLogin = new Date();
        this.favoriteQuestions = favoriteQuestions;
        this.completedQuestions = completedQuestions;
        this.trustedRoles = trustedRoles;
        this.userQuestions = userQuestions;
    }

    // getters
    public int getDailyStreak() {return dailyStreak;}
    public Date getLastLogin() {return lastLogin;}
    public ArrayList<Question> getFavoriteQuestions() {return favoriteQuestions;}
    public ArrayList<Question> getCompletedQuestions() {return completedQuestions;}
    public ArrayList<QuestionTag> getTrustedRoles() {return trustedRoles;}
    public ArrayList<Question> getUserQuestions() {return userQuestions;}

    // setters (stubs)
    public void setDailyStreak(int dailyStreak) {
        this.dailyStreak = dailyStreak;
    }
    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
    public void setTrustedRoles(ArrayList<QuestionTag> questionTags) {
        this.trustedRoles = questionTags;
    }
    public void setUserQuestions(ArrayList<Question> userQuestions) {
        this.userQuestions = userQuestions;
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
        trustedRoles.add(questionTag);
    }

    // adds a user-created question if the student has the right trusted roles (stub)
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
        for(QuestionTag tag: trustedRoles) {
            if(tag.equals(questionTag))
                return trustedRoles.remove(tag);
        }
        return false;
    }

    // removes a user-created question by UUID (stub)
    public boolean removeUserQuestion(UUID Question) {
        for(Question question: userQuestions) {
            if(question.getQuestionID().equals(Question))
               return userQuestions.remove(question); 
        }
        return false;
    }

    // updates daily streak based on new login date (stub)
    public void updateDailyStreak(Date newLogin) {
        Calendar c = Calendar.getInstance();
        c.setTime(lastLogin);
        c.add(Calendar.DATE, 1);
        Date dayAfter = c.getTime();
        if(newLogin.after(dayAfter)) {
            this.dailyStreak = 1;
            setLastLogin(newLogin);
        }
        this.dailyStreak++;
        setLastLogin(newLogin);
        
    }

    // returns a hint segment for the current question (stub)
    public Segment viewHint() {
        return null;
    }
}