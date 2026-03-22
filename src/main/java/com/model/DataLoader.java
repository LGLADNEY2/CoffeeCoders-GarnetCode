package com.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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

            for (Object questionObj : questionsJSON) {
                JSONObject questionJSON = (JSONObject) questionObj;

                String questionIDStr = (String) questionJSON.get(QUESTION_ID);
                String authorIDStr = (String) questionJSON.get(AUTHOR_ID);
               
                UUID questionID = UUID.fromString(questionIDStr);
                UUID authorID = UUID.fromString(authorIDStr);

                String title = (String) questionJSON.get(QUESTION_TITLE);
                String datePosted = (String) questionJSON.get(QUESTION_DATE_POSTED);
                Difficulty difficulty = Difficulty.valueOf((String) questionJSON.get(QUESTION_DIFFICULTY));

                int rating = toInt(questionJSON.get(QUESTION_RATING));
                int totalRatings = toInt(questionJSON.get(QUESTION_TOTAL_RATINGS));
                int recommendedTime = toInt(questionJSON.get(QUESTION_RECOMMENDED_TIME));

                JSONArray segmentsJSON = (JSONArray) questionJSON.get(QUESTION_SEGMENTS);
                ArrayList<Segment> segments = new ArrayList<>();
                if (segmentsJSON != null) {
                    for (Object segObj : segmentsJSON) {
                        JSONObject segJSON = (JSONObject) segObj;
                        segments.add(new Segment(
                            (String) segJSON.get(SEGMENT_TITLE),
                            (String) segJSON.get(SEGMENT_DESC),
                            DataType.valueOf((String) segJSON.get(SEGMENT_DATA_TYPE)),
                            (String) segJSON.get(SEGMENT_DATA)
                        ));
                    }
                }

                JSONObject tagJSON = (JSONObject) questionJSON.get(QUESTION_QUESTION_TAG);
                ArrayList<Category> categories = new ArrayList<>();
                ArrayList<Language> languages = new ArrayList<>();
                ArrayList<Course> courses = new ArrayList<>();
                if (tagJSON != null) {
                    JSONArray categoriesJSON = (JSONArray) tagJSON.get(QUESTION_TAG_CATEGORY);
                    if (categoriesJSON != null) {
                        for (Object catObj : categoriesJSON) {
                            categories.add(Category.valueOf((String) catObj));
                        }
                    }

                    JSONArray languagesJSON = (JSONArray) tagJSON.get(QUESTION_TAG_LANGUAGE);
                    if (languagesJSON != null) {
                        for (Object langObj : languagesJSON) {
                            languages.add(Language.valueOf((String) langObj));
                        }
                    }

                    JSONArray coursesJSON = (JSONArray) tagJSON.get(QUESTION_TAG_COURSE);
                    if (coursesJSON != null) {
                        for (Object courseObj : coursesJSON) {
                            courses.add(Course.valueOf((String) courseObj));
                        }
                    }
                }
                QuestionTag questionTag = new QuestionTag(categories, languages, courses);

                JSONArray hintsJSON = (JSONArray) questionJSON.get(QUESTION_HINTS);
                ArrayList<Segment> hints = new ArrayList<>();
                if (hintsJSON != null) {
                    for (Object hintObj : hintsJSON) {
                        JSONObject hintJSON = (JSONObject) hintObj;
                        hints.add(new Segment(
                            (String) hintJSON.get(SEGMENT_TITLE),
                            (String) hintJSON.get(SEGMENT_DESC),
                            DataType.valueOf((String) hintJSON.get(SEGMENT_DATA_TYPE)),
                            (String) hintJSON.get(SEGMENT_DATA)
                        ));
                    }
                }

                JSONArray solutionsJSON = (JSONArray) questionJSON.get(QUESTION_SOLUTIONS);
                ArrayList<Solution> solutions = new ArrayList<>();
                if (solutionsJSON != null) {
                    for (Object solObj : solutionsJSON) {
                        JSONObject solJSON = (JSONObject) solObj;
                        String solAuthorIDStr = (String) solJSON.get(SOLUTION_AUTHOR_ID);
                        String solLanguageStr = (String) solJSON.get(SOLUTION_LANGUAGE);

                        UUID solAuthorID = UUID.fromString(solAuthorIDStr);
                        String solTitle = (String) solJSON.get(SOLUTION_TITLE);
                        Language solLanguage = Language.valueOf(solLanguageStr);

                        JSONArray solSegmentsJSON = (JSONArray) solJSON.get(SOLUTION_SEGMENTS);
                        ArrayList<Segment> solSegments = new ArrayList<>();
                        if (solSegmentsJSON != null) {
                            for (Object ssObj : solSegmentsJSON) {
                                JSONObject ssJSON = (JSONObject) ssObj;
                                solSegments.add(new Segment(
                                    (String) ssJSON.get(SEGMENT_TITLE),
                                    (String) ssJSON.get(SEGMENT_DESC),
                                    DataType.valueOf((String) ssJSON.get(SEGMENT_DATA_TYPE)),
                                    (String) ssJSON.get(SEGMENT_DATA)
                                ));
                            }
                        }

                        ArrayList<Comment> solComments = parseComments((JSONArray) solJSON.get(SOLUTION_COMMENTS));
                        int likes = toInt(solJSON.get(SOLUTION_LIKES));
                        //todo maaybe remove approved bool
                        solutions.add(new Solution(solAuthorID, solTitle, solLanguage, solSegments, solComments, likes));
                    }
                }

                ArrayList<Comment> comments = parseComments((JSONArray) questionJSON.get(QUESTION_COMMENTS));

                questions.add(new Question(
                    questionID,
                    authorID,
                    title,
                    datePosted,
                    rating,
                    totalRatings,
                    recommendedTime,
                    difficulty,
                    segments,
                    questionTag,
                    hints,
                    solutions,
                    comments
                ));
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return questions;
    }

    private static ArrayList<Comment> parseComments(JSONArray commentsJSON) {
        ArrayList<Comment> comments = new ArrayList<>();
        if (commentsJSON == null) {
            return comments;
        }

        for (Object commentObj : commentsJSON) {
            JSONObject commentJSON = (JSONObject) commentObj;
            String text = (String) commentJSON.get(COMMENT_TEXT);
            String accountIDStr = (String) commentJSON.get(COMMENT_ACCOUNT_ID);

            UUID accountID = UUID.fromString(accountIDStr);
            String datePosted = (String) commentJSON.get(COMMENT_DATE_POSTED);
            int likes = toInt(commentJSON.get(COMMENT_LIKES));
            ArrayList<Comment> replies = parseComments((JSONArray) commentJSON.get(COMMENT_REPLIES));
            comments.add(new Comment(text, accountID, replies, likes, datePosted));
        }

        return comments;
    }

    private static int toInt(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        if (value instanceof String) {
            String str = (String) value;
            if (!str.isEmpty()) {
                return Integer.parseInt(str);
            }
        }
        return 0;
    }
}
