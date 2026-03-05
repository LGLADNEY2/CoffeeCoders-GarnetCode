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
                    
                    // Parse favorite questions UUIDs
                    JSONArray favQuestionsJSON = (JSONArray) accountJSON.get(STUDENT_FAVORITE_QUESTIONS);
                    ArrayList<UUID> favoriteQuestionIDs = new ArrayList<>();
                    for (Object qIDObj : favQuestionsJSON) {
                        favoriteQuestionIDs.add(UUID.fromString((String) qIDObj));
                    }
                    
                    // Parse completed questions UUIDs
                    JSONArray compQuestionsJSON = (JSONArray) accountJSON.get(STUDENT_COMPLETED_QUESTIONS);
                    ArrayList<UUID> completedQuestionIDs = new ArrayList<>();
                    for (Object qIDObj : compQuestionsJSON) {
                        completedQuestionIDs.add(UUID.fromString((String) qIDObj));
                    }
                    
                    // Parse user questions UUIDs
                    JSONArray userQuestionsJSON = (JSONArray) accountJSON.get(STUDENT_USER_QUESTIONS);
                    ArrayList<UUID> userQuestionIDs = new ArrayList<>();
                    for (Object qIDObj : userQuestionsJSON) {
                        userQuestionIDs.add(UUID.fromString((String) qIDObj));
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
                    
                    // Create student - Note: This may need adjustment based on Student constructor
                    Student student = new Student(username, password);
                    // Set inherited Account fields using setters
                    student.setFirstName(firstName);
                    student.setLastName(lastName);
                    student.setEmail(email);
                    student.setUsername(username);
                    student.setPassword(password);
                    
                    accounts.add(student);
                }
            }
            
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return accounts;
    }

}
