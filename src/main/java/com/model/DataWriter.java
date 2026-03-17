package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants{

    public static boolean saveAccounts() {
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
            JSONObject completedQuestions = new JSONObject();
            for(int i = 0; i < ((Student)account).getCompletedQuestions().size(); ++i) {
                completedQuestions.put(QUESTION_ID, ((Student)account).getCompletedQuestions().get(i).getQuestionID().toString());
            }
            accountDetails.put(STUDENT_COMPLETED_QUESTIONS, completedQuestions);
            accountDetails.put(STUDENT_DAILY_STREAK, ((Student)account).getDailyStreak());
            JSONObject favoriteQuestions = new JSONObject();
            for(int i = 0; i < ((Student)account).getFavoriteQuestions().size(); ++i) {
                favoriteQuestions.put(QUESTION_ID, ((Student)account).getFavoriteQuestions().get(i).getQuestionID().toString());
            }
            accountDetails.put(STUDENT_FAVORITE_QUESTIONS, favoriteQuestions);
            JSONObject trustedRoles = new JSONObject();
            for(int i = 0; i < ((Student)account).getFavoriteQuestions().size(); ++i) {
                JSONObject category = new JSONObject();
                JSONObject language = new JSONObject();
                JSONObject course = new JSONObject();
                category.put(QUESTION_TAG_CATEGORY, ((Student)account).getTrustedRoles().get(i).getCategory().get(1));
                language.put(QUESTION_TAG_LANGUAGE, ((Student)account).getTrustedRoles().get(i).getLanguage().get(1));
                course.put(QUESTION_TAG_COURSE, ((Student)account).getTrustedRoles().get(i).getCourse().get(1));
                trustedRoles.put(QUESTION_TAG_CATEGORY, category);
                trustedRoles.put(QUESTION_TAG_LANGUAGE, language);
                trustedRoles.put(QUESTION_TAG_COURSE, course);
            }
            accountDetails.put(STUDENT_TRUSTED_ROLES, trustedRoles);
            accountDetails.put(STUDENT_LAST_LOGIN, ((Student)account).getLastLogin());
        } else if(account.getRole().equals(Role.EDITOR)) {
            accountDetails.put(EDITOR_ADMIN, ((Editor)account).getAdmin());
            JSONObject completedQuestions = new JSONObject();
            for(int i = 0; i < ((Student)account).getCompletedQuestions().size(); ++i) {
                completedQuestions.put(QUESTION_ID, ((Student)account).getCompletedQuestions().get(i).getQuestionID().toString());
            }
            accountDetails.put(STUDENT_COMPLETED_QUESTIONS, completedQuestions);
            accountDetails.put(STUDENT_DAILY_STREAK, ((Student)account).getDailyStreak());
            JSONObject favoriteQuestions = new JSONObject();
            for(int i = 0; i < ((Student)account).getFavoriteQuestions().size(); ++i) {
                favoriteQuestions.put(QUESTION_ID, ((Student)account).getFavoriteQuestions().get(i).getQuestionID().toString());
            }
            accountDetails.put(STUDENT_FAVORITE_QUESTIONS, favoriteQuestions);
            accountDetails.put(STUDENT_LAST_LOGIN, ((Student)account).getLastLogin());
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
        questionDetails.put(QUESTION_RECOMMENDED_TIME, question.getRecommendedTime());
        questionDetails.put(QUESTION_DIFFICULTY, question.getDifficulty().toString());
        JSONObject segments = new JSONObject();
        for(int i = 0; i < question.getSegments().size(); ++i) {
            segments.put(SEGMENT_TITLE, question.getSegments().get(i).getTitle());
            segments.put(SEGMENT_DESC, question.getSegments().get(i).getDesc());
            segments.put(SEGMENT_DATA_TYPE, question.getSegments().get(i).getDataType());
            segments.put(SEGMENT_DATA, question.getSegments().get(i).getData());
        }
        questionDetails.put(QUESTION_SEGMENTS, segments);
        JSONObject hints = new JSONObject();
        questionDetails.put(QUESTION_HINTS, hints);
        for(int i = 0; i < question.getHints().size(); ++i) {
            segments.put(SEGMENT_TITLE, question.getHints().get(i).getTitle());
            segments.put(SEGMENT_DESC, question.getHints().get(i).getDesc());
            segments.put(SEGMENT_DATA_TYPE, question.getHints().get(i).getDataType());
            segments.put(SEGMENT_DATA, question.getHints().get(i).getData());
        }
        questionDetails.put(QUESTION_HINTS, hints);
        JSONObject comments = new JSONObject();
        ArrayList<Comment> start = question.getComments();
        questionDetails.put(QUESTION_COMMENTS, commentR(start, comments));
        return questionDetails;
    }
    public static JSONObject commentR(ArrayList<Comment> comments, JSONObject object) {
        if(comments.get(0) == null)
            return null;
        for(int i=0; i < comments.size(); ++i) {
            object.put(COMMENT_ACCOUNT_ID, comments.get(i).getAccountID());
            object.put(COMMENT_DATE_POSTED, comments.get(i).getDatePosted());
            object.put(COMMENT_LIKES, comments.get(i).getLikes());
            object.put(COMMENT_TEXT, comments.get(i).getText());
            JSONObject objectR = new JSONObject();
            object.put(COMMENT_REPLIES, commentR(comments.get(i).getReplies(), objectR));
        }
        return object;
    }
}
