package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants{

    public static boolean saveAccounts(ArrayList<Account> accounts2) {
        AccountList accountList = AccountList.getInstance();
        ArrayList<Account> accounts = accountList.getAccounts();
        JSONArray jsonAccounts = new JSONArray();

        for(int i=0; i<accounts.size(); ++i) {
            jsonAccounts.add(getAccountJSON(accounts.get(i)));
        }

        try (FileWriter file = new FileWriter(ACCOUNT_TEMP_FILE_NAME)) {
            file.write(jsonAccounts.toJSONString());
            file.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static JSONObject getAccountJSON(Account account) {
        JSONObject accountDetails = new JSONObject();
        accountDetails.put(ACCOUNT_ID, account.getAccountID().toString());
        accountDetails.put(ACCOUNT_USER_NAME, account.getUsername());
        accountDetails.put(ACCOUNT_PASSWORD, account.getPassword());
        accountDetails.put(ACCOUNT_FIRST_NAME, account.getFirstName());
        accountDetails.put(ACCOUNT_LAST_NAME, account.getLastName());
        accountDetails.put(ACCOUNT_EMAIL, account.getEmail());
        accountDetails.put(ACCOUNT_ROLE, account.getRole().toString());
        if(account.getRole().equals(Role.STUDENT)) {
            accountDetails.put(STUDENT_COMPLETED_QUESTIONS, ((Student)account).getCompletedQuestions());
            accountDetails.put(STUDENT_DAILY_STREAK, ((Student)account).getDailyStreak());
            accountDetails.put(STUDENT_FAVORITE_QUESTIONS, ((Student)account).getFavoriteQuestions());
            accountDetails.put(STUDENT_TRUSTED_ROLES, ((Student)account).getFavoriteQuestions());
            accountDetails.put(STUDENT_LAST_LOGIN, ((Student)account).getLastLogin());
        } else if(account.getRole().equals(Role.EDITOR)) {
            accountDetails.put(EDITOR_ADMIN, ((Editor)account).getAdmin());
        }
        return accountDetails;
    }

    public static boolean saveQuestions() {
        QuestionList questionList = QuestionList.getInstance();
        ArrayList<Question> questions = questionList.getQuestions();
        JSONArray jsonQuestions = new JSONArray();

        for(int i=0; i<questions.size(); ++i) {
            jsonQuestions.add(getQuestionJSON(questions.get(i)));
        }

        try (FileWriter file = new FileWriter(QUESTION_TEMP_FILE_NAME)) {
            file.write(jsonQuestions.toJSONString());
            file.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static JSONObject getQuestionJSON(Question question) {
        JSONObject questionDetails = new JSONObject();
        questionDetails.put(QUESTION_ID, question.getQuestionID().toString());
        questionDetails.put(AUTHOR_ID, question.getAuthorID());
        questionDetails.put(QUESTION_TITLE, question.getTitle());
        questionDetails.put(QUESTION_RATING, question.getRating());
        questionDetails.put(QUESTION_DATE_POSTED, question.getDatePosted());
        questionDetails.put(QUESTION_RECOMENDED_TIME, question.getRecommendedTime());
        questionDetails.put(QUESTION_DIFFICULTY, question.getDifficulty().toString());
        questionDetails.put(QUESTION_SEGMENTS, question.getSegments());
        questionDetails.put(QUESTION_QUESTION_TAG, question.getQuestionTag());
        questionDetails.put(QUESTION_HINTS, question.getHints());
        questionDetails.put(QUESTION_COMMENTS, question.getComments());

        return questionDetails;
    }
}
