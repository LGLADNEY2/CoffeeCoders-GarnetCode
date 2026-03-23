package com.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents an editor account with elevated permissions for question management.
 * @author Coffee Coders
 */
public class Editor extends Student{
    private boolean admin;

    /**
     * Creates an editor account with default admin privileges.
     *
     * @param username editor username
     * @param password editor password
     */
    public Editor(String username, String password) {
        super(username, password);
        this.admin = true;
    }

    /**
     * Creates an editor account with generated account ID.
     *
     * @param firstName account first name
     * @param lastName account last name
     * @param email account email
     * @param username account username
     * @param password account password
     * @param role account role
     * @param dailyStreak current daily streak
     * @param favoriteQuestions list of favorite questions
     * @param completedQuestions list of completed questions
     * @param trustedRoles list of trusted role tags
     * @param userQuestions list of user-created questions
     */
    public Editor(String firstName, String lastName, String email, String username, String password, Role role, int dailyStreak, ArrayList<Question> favoriteQuestions, ArrayList<Question> completedQuestions, ArrayList<QuestionTag> trustedRoles, ArrayList<Question> userQuestions) {
        super(UUID.randomUUID(), firstName, lastName, email, username, password, role, dailyStreak, favoriteQuestions, completedQuestions, trustedRoles, userQuestions);
        this.admin = false;
    }
    
    /**
     * Creates an editor account with all fields provided.
     *
     * @param accountID account ID
     * @param firstName account first name
     * @param lastName account last name
     * @param email account email
     * @param username account username
     * @param password account password
     * @param role account role
     * @param dailyStreak current daily streak
     * @param favoriteQuestions list of favorite questions
     * @param completedQuestions list of completed questions
     * @param trustedRoles list of trusted role tags
     * @param userQuestions list of user-created questions
     * @param admin whether this editor has admin privileges
     */
    public Editor(UUID accountID, String firstName, String lastName, String email, String username, String password, Role role, int dailyStreak, ArrayList<Question> favoriteQuestions, ArrayList<Question> completedQuestions, ArrayList<QuestionTag> trustedRoles, ArrayList<Question> userQuestions, boolean admin) {
        super(accountID, firstName, lastName, email, username, password, role, dailyStreak, favoriteQuestions, completedQuestions, trustedRoles, userQuestions);
        this.admin = admin;
    }

    /**
     * Returns whether this editor has admin privileges.
     *
     * @return true if admin, otherwise false
     */
    public boolean getAdmin() {return admin;}

    /**
     * Sets whether this editor has admin privileges.
     *
     * @param admin new admin status
     */
    public void setAdmin(boolean admin) {
        
    }

    /**
     * Creates a question as an editor.
     *
     * @param question question to create
     * @return result of the create attempt
     */
    public boolean createQuestion(Question question) {
        return true;
    }

    /**
     * Edits an existing question as an editor.
     *
     * @param question question to edit
     * @return result of the edit attempt
     */
    public boolean editQuestion(Question question) {
        return true;
    }

    /**
     * Creates a solution for a question as an editor.
     *
     * @param question question to attach the solution to
     * @param solution solution to create
     * @return result of the create attempt
     */
    public boolean createSolution(Question question, Solution solution) {
        return true;
    }

    /**
     * Approves a question when authorized by admin status.
     *
     * @param admin admin status of the acting user
     * @param question question to approve
     * @return result of the approval attempt
     */
    public boolean approveQuestion(boolean admin, Question question) {
        return true;
    }

    /**
     * Approves a solution when authorized by admin status.
     *
     * @param admin admin status of the acting user
     * @param solution solution to approve
     * @return result of the approval attempt
     */
    public boolean approveSolution(boolean admin, Solution solution) {
        return true;
    }

    /**
     * Removes a question when authorized by admin status.
     *
     * @param admin admin status of the acting user
     * @param question question to remove
     * @return result of the remove attempt
     */
    public boolean removeQuestion(boolean admin, Question question) {
        return true;
    }

    /**
     * Removes a solution when authorized by admin status.
     *
     * @param admin admin status of the acting user
     * @param solution solution to remove
     * @return result of the remove attempt
     */
    public boolean removeSolution(boolean admin, Solution solution) {
        return true;
    }

    /**
     * Adds a trusted role tag for a target account when authorized.
     *
     * @param admin admin status of the acting user
     * @param accountID target account ID
     * @param questionTag trusted role tag to add
     * @return result of the add attempt
     */
    public boolean addTrustedRole(boolean admin, UUID accountID, QuestionTag questionTag) {
        return true;
    }

    /**
     * Grants admin privileges to the specified account.
     *
     * @param accountID account ID to promote
     * @return result of the promotion attempt
     */
    public boolean addAdmin(UUID accountID) {
        return true;
    }

    /**
     * Removes admin privileges from the specified account.
     *
     * @param accountID account ID to demote
     * @return result of the demotion attempt
     */
    public boolean removeAdmin(UUID accountID) {
        return true;
    }
}