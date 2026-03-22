package com.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import static com.model.DataType.HINT;
import static com.model.DataType.STRING;

public class DataLoader extends DataConstants {
    // get accounts method that returns an array list of loaded accounts, made by the json file of accounts
    public static ArrayList<Account> getAccounts() {
        // new empty array list for the accounts
        ArrayList<Account> accounts = new ArrayList<>();
        // new empty array list for all of the questions into one array list, loaded by getquestions method
        // the getquestions returns just all of the questions,
        // the getaccounts method splits it up by tag later on
        ArrayList<Question> allQuestions = getQuestions();
        
        // try block, catches errors and returns it after instead of just stopping program
        try {
            // filereader object that takes account file name from data constants
            FileReader reader = new FileReader(ACCOUNT_FILE_NAME);
            // new json parser object
            JSONParser parser = new JSONParser();
            // making a json array that is the file reader passed inside the json parser, then cast to an array
            JSONArray accountsJSON = (JSONArray) parser.parse(reader);
            
            // for every account json object in the json array accountsJSON,
            for (int i = 0; i < accountsJSON.size(); i++) {
                // make and assign a json object of this index from the array list
                JSONObject accountJSON = (JSONObject) accountsJSON.get(i);
                // now get all of the parameters of this single account
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
                    for (Object quesID : favQuestionsJSON) {
                        UUID questionID = UUID.fromString((String) quesID);
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

    // return an arraylist of all questions from the json file 
    // TODO parse comments
    public static ArrayList<Question> getQuestions() {
        ArrayList<Question> questions = new ArrayList<>();

        try {
            // Reader FileReader object to read the file of all questions
            FileReader reader = new FileReader(QUESTION_FILE_NAME);
            // Parser JSONParser to allow reading the JSON file of all questions
            JSONParser parser = new JSONParser();
            // Putting the reader through the parser to allow reading of a JSON file properly
            JSONArray questionsJSON = (JSONArray) parser.parse(reader);

            // for every question in the json file,
            for (int i = 0; i < questionsJSON.size(); i++) {
                // make a json object for each question in the json file
                JSONObject questionJSON = (JSONObject) questionsJSON.get(i);

                UUID questionID = UUID.fromString((String) questionJSON.get(QUESTION_ID));
                UUID authorID = UUID.fromString((String) questionJSON.get(AUTHOR_ID));
                String title = (String) questionJSON.get(QUESTION_TITLE);
                int rating = ((Long) questionJSON.get(QUESTION_RATING)).intValue();
                String datePosted = (String) questionJSON.get(QUESTION_DATE_POSTED);
                int recommendedTime = ((Long) questionJSON.get(QUESTION_RECOMMENDED_TIME)).intValue();
                Difficulty difficulty = Difficulty.valueOf((String) questionJSON.get(QUESTION_DIFFICULTY));

                
                // ================ segments ==================== //
                // make a json array of the segments of each question, which is title, desc, data type, and data
                // each "segments" instance is an array of these 4
                JSONArray segmentsJSON = (JSONArray) questionJSON.get(QUESTION_SEGMENTS);
                // make arraylist of Segment
                ArrayList<Segment> segments = new ArrayList<>();
                // for each segment in the segment json array,
                for (Object segObj : segmentsJSON) {

                    // make a json object of that segment
                    JSONObject segJSON = (JSONObject) segObj;
                    // both add to the segments arraylist of Segments, and make a new Segment object with the data
                    segments.add(new Segment(
                        (String) segJSON.get(SEGMENT_TITLE),
                        (String) segJSON.get(SEGMENT_DESC),
                        (String) segJSON.get(SEGMENT_DATA_TYPE),
                        (String) segJSON.get(SEGMENT_DATA)
                    ));
                }

                // ======================= question tags ======================= //
                // make new tag json object
                JSONObject tagJSON = (JSONObject) questionJSON.get(QUESTION_QUESTION_TAG);
                // make array list of categories
                ArrayList<Category> categories = new ArrayList<>();
                for (Object catObj : (JSONArray) tagJSON.get(QUESTION_TAG_CATEGORY)) {
                    categories.add(Category.valueOf((String) catObj));
                }
                // make array list of languages
                ArrayList<Language> languages = new ArrayList<>();
                for (Object langObj : (JSONArray) tagJSON.get(QUESTION_TAG_LANGUAGE)) {
                    languages.add(Language.valueOf((String) langObj));
                }
                // make array list of courses
                ArrayList<Course> courses = new ArrayList<>();
                for (Object courseObj : (JSONArray) tagJSON.get(QUESTION_TAG_COURSE)) {
                    courses.add(Course.valueOf((String) courseObj));
                }
                QuestionTag questionTag = new QuestionTag(categories, languages, courses);

                // ===================== hints ===================== //
                // make json array of hints
                JSONArray hintsJSON = (JSONArray) questionJSON.get(QUESTION_HINTS);
                // make array list of segments for hints, hint 
                ArrayList<Segment> hints = new ArrayList<>();
                for (Object hintObj : hintsJSON) {
                    hints.add(new Segment("Hint", (String) hintObj, "STRING", ""));
                }

                // ================ solutions ================== //
                // make json array
                JSONArray solutionsJSON = (JSONArray) questionJSON.get(QUESTION_SOLUTIONS);
                // make an empty array list of solutions that will be returned at the end
                ArrayList<Solution> solutions = new ArrayList<>();
                // if the json file has at least questions in it, continue
                if (solutionsJSON != null) {
                    // for every json object in the solution json jsonarray,
                    for (Object solObj : solutionsJSON) {
                        // get all of the variables
                        JSONObject solJSON = (JSONObject) solObj;
                        String solAuthorIDStr = (String) solJSON.get(SOLUTION_AUTHOR_ID);
                        UUID solAuthorID = UUID.fromString(solAuthorIDStr);
                        String solTitle = (String) solJSON.get(SOLUTION_TITLE);
                        Language solLanguage = Language.valueOf((String) solJSON.get(SOLUTION_LANGUAGE));
                        JSONArray solSegmentsJSON = (JSONArray) solJSON.get(SOLUTION_SEGMENTS);
                        // make empty segement arraylist to hold the specific soltuions segments
                        ArrayList<Segment> solSegments = new ArrayList<>();
                        for (Object ssObj : solSegmentsJSON) {
                            JSONObject ssJSON = (JSONObject) ssObj;
                            solSegments.add(new Segment(
                                (String) ssJSON.get(SEGMENT_TITLE),
                                (String) ssJSON.get(SEGMENT_DESC),
                                (String) ssJSON.get(SEGMENT_DATA_TYPE),
                                (String) ssJSON.get(SEGMENT_DATA)
                            ));
                        }
                        Object approvedObj = solJSON.get(SOLUTION_APPROVED);
                        boolean approved = (approvedObj instanceof Boolean) ? (Boolean) approvedObj : false;
                        solutions.add(new Solution(solAuthorID, solTitle, solLanguage, solSegments, approved));
                    }
                }

                // ======== comments ========== //
                // make empty array list of comments 
                ArrayList<Comment> comments = new ArrayList<>();
                String commentText = (String) questionJSON.get(COMMENT_TEXT);
                UUID commentAccountID = (UUID) questionJSON.get(COMMENT_ACCOUNT_ID);
                int commentLikes = (Integer) questionJSON.get(COMMENT_LIKES);
                String commentDatePosted = (String) questionJSON.get(COMMENT_DATE_POSTED);

                // comments.add(new Comment(commentText, commentAccountID,));

                questions.add(new Question(questionID, authorID, title, rating, datePosted,
                recommendedTime, difficulty, segments, questionTag, hints, solutions, comments));
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return questions;
    }
}
