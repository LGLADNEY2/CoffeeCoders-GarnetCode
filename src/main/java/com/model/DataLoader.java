package com.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import static com.model.DataType.HINT;

public class DataLoader extends DataConstants {
    public static ArrayList<Account> getAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();
        ArrayList<Question> allQuestions = getQuestions();
        
        try {
            FileReader reader = new FileReader(ACCOUNT_FILE_NAME);
            JSONParser parser = new JSONParser();
            JSONArray accountsJSON = (JSONArray) parser.parse(reader);
            
            for (int i = 0; i < accountsJSON.size(); i++) {
                JSONObject accountJSON = (JSONObject) accountsJSON.get(i);
                UUID accountID = UUID.fromString((String) accountJSON.get(ACCOUNT_ID));
                String firstName = (String) accountJSON.get(ACCOUNT_FIRST_NAME);
                String lastName = (String) accountJSON.get(ACCOUNT_LAST_NAME);
                String email = (String) accountJSON.get(ACCOUNT_EMAIL);
                String username = (String) accountJSON.get(ACCOUNT_USER_NAME);
                String password = (String) accountJSON.get(ACCOUNT_PASSWORD);
                String roleStr = (String) accountJSON.get(ACCOUNT_ROLE);
                
                if (roleStr.equalsIgnoreCase("Student")) {
                    int dailyStreak = ((Long) accountJSON.get(STUDENT_DAILY_STREAK)).intValue();
                    
                    // Build favorite questions from stored UUIDs
                    JSONArray favQuestionsJSON = (JSONArray) accountJSON.get(STUDENT_FAVORITE_QUESTIONS);
                    ArrayList<Question> favoriteQuestions = new ArrayList<>();
                    for (Object qIDObj : favQuestionsJSON) {
                        UUID questionID = UUID.fromString((String) qIDObj);
                        for (Question question : allQuestions) {
                            if (question.getQuestionID().equals(questionID)) {
                                favoriteQuestions.add(question);
                                break;
                            }
                        }
                    }
                    
                    // Build completed questions from stored UUIDs
                    JSONArray compQuestionsJSON = (JSONArray) accountJSON.get(STUDENT_COMPLETED_QUESTIONS);
                    ArrayList<Question> completedQuestions = new ArrayList<>();
                    for (Object qIDObj : compQuestionsJSON) {
                        UUID questionID = UUID.fromString((String) qIDObj);
                        for (Question question : allQuestions) {
                            if (question.getQuestionID().equals(questionID)) {
                                completedQuestions.add(question);
                                break;
                            }
                        }
                    }
                    
                    // Build user questions from stored UUIDs
                    JSONArray userQuestionsJSON = (JSONArray) accountJSON.get(STUDENT_USER_QUESTIONS);
                    ArrayList<Question> userQuestions = new ArrayList<>();
                    for (Object qIDObj : userQuestionsJSON) {
                        UUID questionID = UUID.fromString((String) qIDObj);
                        for (Question question : allQuestions) {
                            if (question.getQuestionID().equals(questionID)) {
                                userQuestions.add(question);
                                break;
                            }
                        }
                    }
                    
                    // Parse trusted roles
                    JSONArray trustedRolesJSON = (JSONArray) accountJSON.get(STUDENT_TRUSTED_ROLES);
                    ArrayList<QuestionTag> trustedRoles = new ArrayList<>();
                    for (Object roleObj : trustedRolesJSON) {
                        JSONObject roleJSON = (JSONObject) roleObj;
                        
                        // Parse categories
                        JSONArray categoriesJSON = (JSONArray) roleJSON.get(QUESTION_TAG_CATEGORY);
                        ArrayList<Category> categories = new ArrayList<>();
                        for (Object catObj : categoriesJSON) {
                            categories.add(Category.valueOf((String) catObj));
                        }
                        
                        // Parse languages
                        JSONArray languagesJSON = (JSONArray) roleJSON.get(QUESTION_TAG_LANGUAGE);
                        ArrayList<Language> languages = new ArrayList<>();
                        for (Object langObj : languagesJSON) {
                            languages.add(Language.valueOf((String) langObj));
                        }
                        
                        // Parse courses
                        JSONArray coursesJSON = (JSONArray) roleJSON.get("course");
                        ArrayList<Course> courses = new ArrayList<>();
                        for (Object courseObj : coursesJSON) {
                            courses.add(Course.valueOf((String) courseObj));
                        }
                        
                        trustedRoles.add(new QuestionTag(categories, languages, courses));
                    }
                    
                        Student student = new Student(accountID, firstName, lastName, email, username, password,
                            Role.STUDENT, dailyStreak, favoriteQuestions, completedQuestions, trustedRoles,
                            userQuestions);
                    
                    accounts.add(student);
                }
            }
            
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return accounts;
    }

    public static ArrayList<Question> getQuestions() {
        ArrayList<Question> questions = new ArrayList<>();

        try {
            FileReader reader = new FileReader(QUESTION_FILE_NAME);
            JSONParser parser = new JSONParser();
            JSONArray questionsJSON = (JSONArray) parser.parse(reader);

            for (int i = 0; i < questionsJSON.size(); i++) {
                JSONObject questionJSON = (JSONObject) questionsJSON.get(i);

                // Skip blank placeholder entries
                String questionIDStr = (String) questionJSON.get(QUESTION_ID);
                if (questionIDStr == null || questionIDStr.isEmpty()) continue;

                UUID questionID = UUID.fromString(questionIDStr);
                UUID authorID = UUID.fromString((String) questionJSON.get(AUTHOR_ID));
                String title = (String) questionJSON.get(QUESTION_TITLE);
                int rating = ((Long) questionJSON.get(QUESTION_RATING)).intValue();
                int totalRatings = ((Long) questionJSON.get(QUESTION_TOTAL_RATINGS)).intValue();
                ArrayList<Comment> comments = new ArrayList<>();
                String datePosted = (String) questionJSON.get(QUESTION_DATE_POSTED);
                int recommendedTime = ((Long) questionJSON.get(QUESTION_RECOMMENDED_TIME)).intValue();
                Difficulty difficulty = Difficulty.valueOf((String) questionJSON.get(QUESTION_DIFFICULTY));

                // Parse segments
                JSONArray segmentsJSON = (JSONArray) questionJSON.get(QUESTION_SEGMENTS);
                ArrayList<Segment> segments = new ArrayList<>();
                for (Object segObj : segmentsJSON) {
                    JSONObject segJSON = (JSONObject) segObj;
                    segments.add(new Segment(
                        (String) segJSON.get(SEGMENT_TITLE),
                        (String) segJSON.get(SEGMENT_DESC),
                        (DataType) segJSON.get(SEGMENT_DATA_TYPE),
                        (String) segJSON.get(SEGMENT_DATA)
                    ));
                }

                // Parse questionTag
                JSONObject tagJSON = (JSONObject) questionJSON.get(QUESTION_QUESTION_TAG);
                ArrayList<Category> categories = new ArrayList<>();
                for (Object catObj : (JSONArray) tagJSON.get(QUESTION_TAG_CATEGORY)) {
                    categories.add(Category.valueOf((String) catObj));
                }
                ArrayList<Language> languages = new ArrayList<>();
                for (Object langObj : (JSONArray) tagJSON.get(QUESTION_TAG_LANGUAGE)) {
                    languages.add(Language.valueOf((String) langObj));
                }
                ArrayList<Course> courses = new ArrayList<>();
                for (Object courseObj : (JSONArray) tagJSON.get(QUESTION_TAG_COURSE)) {
                    courses.add(Course.valueOf((String) courseObj));
                }
                QuestionTag questionTag = new QuestionTag(categories, languages, courses);

                // Parse hints (JSON stores them as plain strings, wrap each as a Segment)
                JSONArray hintsJSON = (JSONArray) questionJSON.get(QUESTION_HINTS);
                ArrayList<Segment> hints = new ArrayList<>();
                for (Object hintObj : hintsJSON) {
                    hints.add(new Segment("Hint", (String) hintObj, HINT, ""));
                }

                // Parse solutions
                JSONArray solutionsJSON = (JSONArray) questionJSON.get(QUESTION_SOLUTIONS);
                ArrayList<Solution> solutions = new ArrayList<>();
                if (solutionsJSON != null) {
                    for (Object solObj : solutionsJSON) {
                        JSONObject solJSON = (JSONObject) solObj;
                        String solAuthorIDStr = (String) solJSON.get(SOLUTION_AUTHOR_ID);
                        if (solAuthorIDStr == null || solAuthorIDStr.isEmpty()) continue;
                        UUID solAuthorID = UUID.fromString(solAuthorIDStr);
                        String solTitle = (String) solJSON.get(SOLUTION_TITLE);
                        Language solLanguage = Language.valueOf((String) solJSON.get(SOLUTION_LANGUAGE));
                        JSONArray solSegmentsJSON = (JSONArray) solJSON.get(SOLUTION_SEGMENTS);
                        ArrayList<Segment> solSegments = new ArrayList<>();
                        for (Object ssObj : solSegmentsJSON) {
                            JSONObject ssJSON = (JSONObject) ssObj;
                            solSegments.add(new Segment(
                                (String) ssJSON.get(SEGMENT_TITLE),
                                (String) ssJSON.get(SEGMENT_DESC),
                                (DataType) ssJSON.get(SEGMENT_DATA_TYPE),
                                (String) ssJSON.get(SEGMENT_DATA)
                            ));
                        }
                        Object approvedObj = solJSON.get(SOLUTION_APPROVED);
                        boolean approved = (approvedObj instanceof Boolean) && (Boolean) approvedObj;
                        //do we need approved boolean?
                        // solutions.add(solAuthorID, solTitle, solLanguage, solSegments, solComments, likes, approved));
                        solutions.add(new Solution(solAuthorID, solTitle, solLanguage, solSegments, solComments, likes, approved));
                    }
                }

                questions.add(new Question(questionID, authorID, title, datePosted, rating, totalRatings, difficulty, segments, questionTag, hints, solutions, comments));
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return questions;
    }
}
