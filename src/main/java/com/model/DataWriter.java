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

        try (FileWriter file = new FileWriter(ACCOUNT_FILE_NAME)) {
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
            JSONArray completedQuestions = new JSONArray();
            for(int i = 0; i < ((Student)account).getCompletedQuestions().size(); ++i) {
            completedQuestions.add(((Student)account).getCompletedQuestions().get(i).getQuestionID().toString());
        }
        accountDetails.put(STUDENT_COMPLETED_QUESTIONS, completedQuestions);

        accountDetails.put(STUDENT_DAILY_STREAK, ((Student)account).getDailyStreak());

        JSONArray favoriteQuestions = new JSONArray();
        for(int i = 0; i < ((Student)account).getFavoriteQuestions().size(); ++i) {
            favoriteQuestions.add(((Student)account).getFavoriteQuestions().get(i).getQuestionID().toString());
        }
        accountDetails.put(STUDENT_FAVORITE_QUESTIONS, favoriteQuestions);

        JSONArray trustedRoles = new JSONArray();
        for(int i = 0; i < ((Student)account).getTrustedRoles().size(); ++i) {
            JSONObject trustedRole = new JSONObject();

            JSONArray categories = new JSONArray();
            for (Category category : ((Student)account).getTrustedRoles().get(i).getCategory()) {
                categories.add(category.toString());
            }

            JSONArray languages = new JSONArray();
            for (Language language : ((Student)account).getTrustedRoles().get(i).getLanguage()) {
                languages.add(language.toString());
            }

            JSONArray courses = new JSONArray();
            for (Course course : ((Student)account).getTrustedRoles().get(i).getCourse()) {
                courses.add(course.toString());
            }

            trustedRole.put(QUESTION_TAG_CATEGORY, categories);
            trustedRole.put(QUESTION_TAG_LANGUAGE, languages);
            trustedRole.put(QUESTION_TAG_COURSE, courses);
            trustedRoles.add(trustedRole);
        }
        accountDetails.put(STUDENT_TRUSTED_ROLES, trustedRoles);

        JSONArray userQuestions = new JSONArray();
        for(int i = 0; i < ((Student)account).getUserQuestions().size(); ++i) {
            userQuestions.add(((Student)account).getUserQuestions().get(i).getQuestionID().toString());
        }
        accountDetails.put(STUDENT_USER_QUESTIONS, userQuestions);

        accountDetails.put(STUDENT_LAST_LOGIN, ((Student)account).getLastLogin().toString());
    } else if(account.getRole().equals(Role.EDITOR)) {
        accountDetails.put(EDITOR_ADMIN, ((Editor)account).getAdmin());

        JSONArray completedQuestions = new JSONArray();
        for(int i = 0; i < ((Student)account).getCompletedQuestions().size(); ++i) {
            completedQuestions.add(((Student)account).getCompletedQuestions().get(i).getQuestionID().toString());
        }
        accountDetails.put(STUDENT_COMPLETED_QUESTIONS, completedQuestions);

        accountDetails.put(STUDENT_DAILY_STREAK, ((Student)account).getDailyStreak());

        JSONArray favoriteQuestions = new JSONArray();
        for(int i = 0; i < ((Student)account).getFavoriteQuestions().size(); ++i) {
            favoriteQuestions.add(((Student)account).getFavoriteQuestions().get(i).getQuestionID().toString());
        }
        accountDetails.put(STUDENT_FAVORITE_QUESTIONS, favoriteQuestions);

        JSONArray trustedRoles = new JSONArray();
        for(int i = 0; i < ((Student)account).getTrustedRoles().size(); ++i) {
            JSONObject trustedRole = new JSONObject();

        JSONArray categories = new JSONArray();
        for (Category category : ((Student)account).getTrustedRoles().get(i).getCategory()) {
            categories.add(category.toString());
        }

        JSONArray languages = new JSONArray();
        for (Language language : ((Student)account).getTrustedRoles().get(i).getLanguage()) {
            languages.add(language.toString());
        }

        JSONArray courses = new JSONArray();
        for (Course course : ((Student)account).getTrustedRoles().get(i).getCourse()) {
            courses.add(course.toString());
        }

        trustedRole.put(QUESTION_TAG_CATEGORY, categories);
        trustedRole.put(QUESTION_TAG_LANGUAGE, languages);
        trustedRole.put(QUESTION_TAG_COURSE, courses);
        trustedRoles.add(trustedRole);
    }
    accountDetails.put(STUDENT_TRUSTED_ROLES, trustedRoles);

        JSONArray userQuestions = new JSONArray();
        for(int i = 0; i < ((Student)account).getUserQuestions().size(); ++i) {
            userQuestions.add(((Student)account).getUserQuestions().get(i).getQuestionID().toString());
    }
        accountDetails.put(STUDENT_USER_QUESTIONS, userQuestions);

        accountDetails.put(STUDENT_LAST_LOGIN, ((Student)account).getLastLogin().toString());
}

        return accountDetails;
    }

    public static boolean saveQuestions() {
        QuestionList questionList = QuestionList.getInstance();
        ArrayList<Question> questions = questionList.getQuestions();
        // Prevent accidental deletion: do not overwrite file if list is empty
        if (questions == null || questions.isEmpty()) {
            System.err.println("[Warning] Attempted to save empty questions list. Aborting save to prevent data loss.");
            return false;
        }
        JSONArray jsonQuestions = new JSONArray();
        for (int i = 0; i < questions.size(); ++i) {
            jsonQuestions.add(getQuestionJSON(questions.get(i)));
        }
        try (FileWriter file = new FileWriter(QUESTION_FILE_NAME)) {
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
        questionDetails.put(QUESTION_TOTAL_RATINGS, question.getTotalRatings());
        questionDetails.put(QUESTION_DATE_POSTED, question.getDatePosted());
        questionDetails.put(QUESTION_RECOMMENDED_TIME, question.getRecommendedTime());
        questionDetails.put(QUESTION_DIFFICULTY, question.getDifficulty().toString());
        JSONArray segments = new JSONArray();
        for(int i = 0; i < question.getSegments().size(); ++i) {
            JSONObject segment = new JSONObject();
            segment.put(SEGMENT_TITLE, question.getSegments().get(i).getTitle());
            segment.put(SEGMENT_DESC, question.getSegments().get(i).getDesc());
            segment.put(SEGMENT_DATA_TYPE, question.getSegments().get(i).getDataType().toString());
            segment.put(SEGMENT_DATA, question.getSegments().get(i).getData());
            segments.add(segment);
        }
        questionDetails.put(QUESTION_SEGMENTS, segments);
        JSONArray hints = new JSONArray();
        
        questionDetails.put(QUESTION_HINTS, hints);
        for(int i = 0; i < question.getHints().size(); ++i) {
            JSONObject hint = new JSONObject();
            hint.put(SEGMENT_TITLE, question.getHints().get(i).getTitle());
            hint.put(SEGMENT_DESC, question.getHints().get(i).getDesc());
            hint.put(SEGMENT_DATA_TYPE, question.getHints().get(i).getDataType().toString());
            hint.put(SEGMENT_DATA, question.getHints().get(i).getData());
            hints.add(hint);
        }
        questionDetails.put(QUESTION_HINTS, hints);
        JSONArray solutions = new JSONArray();
        for(int i = 0; i < question.getSolutions().size(); ++i) {
            JSONObject solution = new JSONObject();
            solution.put(SOLUTION_AUTHOR_ID, question.getSolutions().get(i).getAuthorID());
            solution.put(SOLUTION_LANGUAGE, question.getSolutions().get(i).getLanguage());
            solution.put(SOLUTION_TITLE, question.getSolutions().get(i).getTitle());
            JSONArray solutionSegments = new JSONArray();
            for(int j = 0; j < question.getSolutions().get(i).getSegments().size(); ++j) {
                JSONObject segment = new JSONObject();
                segment.put(SEGMENT_TITLE, question.getSolutions().get(i).getSegments().get(j).getTitle());
                segment.put(SEGMENT_DESC, question.getSolutions().get(i).getSegments().get(j).getDesc());
                segment.put(SEGMENT_DATA_TYPE, question.getSolutions().get(i).getSegments().get(j).getDataType().toString());
                segment.put(SEGMENT_DATA, question.getSolutions().get(i).getSegments().get(j).getData());
                solutionSegments.add(segment);
            }
            solution.put(SOLUTION_SEGMENTS, solutionSegments);
            ArrayList<Comment> start = question.getSolutions().get(i).getComments();
            solution.put(SOLUTION_COMMENTS, getComments(start));
            solution.put(SOLUTION_LIKES, question.getSolutions().get(i).getLikes());
            solutions.add(solution);
        }
        questionDetails.put(QUESTION_SOLUTIONS, solutions);

        ArrayList<Comment> start = question.getComments();
        questionDetails.put(QUESTION_COMMENTS, getComments(start));
        return questionDetails;
    }
    public static JSONArray getComments(ArrayList<Comment> comments) {
        JSONArray jsonComments = new JSONArray();
        if(comments == null || comments.size() == 0 || comments.get(0) == null)
            return jsonComments;
        for(int i=0; i < comments.size(); ++i) {
            JSONObject comment = new JSONObject();
            comment.put(COMMENT_ACCOUNT_ID, comments.get(i).getAccountID());
            comment.put(COMMENT_DATE_POSTED, comments.get(i).getDatePosted());
            comment.put(COMMENT_LIKES, comments.get(i).getLikes());
            comment.put(COMMENT_TEXT, comments.get(i).getText());
            comment.put(COMMENT_REPLIES, getComments(comments.get(i).getReplies()));
            jsonComments.add(comment);
        }
        return jsonComments;
    }
}
