package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants{

    public static boolean saveUsers() {
        UserList userList = UserList.getInstance();
        ArrayList<Account> users = userList.getAccounts();
        JSONArray jsonUsers = new JSONArray();

        for(int i=0; i<users.size(); ++i) {
            jsonUsers.add(getUserJSON(users.get(i)));
        }

        try (FileWriter file = new FileWriter(ACCOUNT_TEMP_FILE_NAME)) {
            file.write(jsonUsers.toJSONString());
            file.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static JSONObject getUserJSON(Editor editor) {
        JSONObject userDetails = new JSONObject();
        userDetails.put(ACCOUNT_ID, editor.getAccountID().toString());
        userDetails.put(ACCOUNT_USER_NAME, editor.getUsername());
        userDetails.put(ACCOUNT_PASSWORD, editor.getPassword());
        userDetails.put(ACCOUNT_FIRST_NAME, editor.getFirstName());
        userDetails.put(ACCOUNT_LAST_NAME, editor.getLastName());
        userDetails.put(ACCOUNT_EMAIL, editor.getEmail());
        userDetails.put(ACCOUNT_ROLE, editor.getRole().toString());
        if(editor.getRole().equals(Role.STUDENT)) {
            userDetails.put(STUDENT_COMPLETED_QUESTIONS, editor.getCompletedQuestions());
            userDetails.put(STUDENT_DAILY_STREAK, editor.getDailyStreak());
            userDetails.put(STUDENT_FAVORITE_QUESTIONS, editor.getFavoriteQuestions());
            userDetails.put(STUDENT_TRUSTED_ROLES, editor.getFavoriteQuestions());
            userDetails.put(STUDENT_LAST_LOGIN, editor.getLastLogin());
        } else {
            userDetails.put(EDITOR_ADMIN, editor.getAdmin());
        }
        return userDetails;
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
