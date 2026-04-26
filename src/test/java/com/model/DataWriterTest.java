package com.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.UUID;


public class DataWriterTest {

    @Test
    public void getAccountJSON_studentAccount_createsCorrectJSONObject() {
        UUID accountID = UUID.randomUUID();
        Student student = new Student(accountID, "John", "Doe", "john@example.com", "johndoe", "password", Role.STUDENT, 5, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        JSONObject json = DataWriter.getAccountJSON(student);

        assertEquals(accountID.toString(), json.get(DataConstants.ACCOUNT_ID));
        assertEquals("johndoe", json.get(DataConstants.ACCOUNT_USER_NAME));
        assertEquals("password", json.get(DataConstants.ACCOUNT_PASSWORD));
        assertEquals("John", json.get(DataConstants.ACCOUNT_FIRST_NAME));
        assertEquals("Doe", json.get(DataConstants.ACCOUNT_LAST_NAME));
        assertEquals("john@example.com", json.get(DataConstants.ACCOUNT_EMAIL));
        assertEquals("STUDENT", json.get(DataConstants.ACCOUNT_ROLE));
        assertEquals(5, json.get(DataConstants.STUDENT_DAILY_STREAK));
    }

    @Test
    public void getAccountJSON_editorAccount_createsCorrectJSONObject() {
        UUID accountID = UUID.randomUUID();
        Editor editor = new Editor(accountID, "Jane", "Smith", "jane@example.com", "janesmith", "password", Role.EDITOR, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), true);

        JSONObject json = DataWriter.getAccountJSON(editor);

        assertEquals(accountID.toString(), json.get(DataConstants.ACCOUNT_ID));
        assertEquals("janesmith", json.get(DataConstants.ACCOUNT_USER_NAME));
        assertEquals("password", json.get(DataConstants.ACCOUNT_PASSWORD));
        assertEquals("Jane", json.get(DataConstants.ACCOUNT_FIRST_NAME));
        assertEquals("Smith", json.get(DataConstants.ACCOUNT_LAST_NAME));
        assertEquals("jane@example.com", json.get(DataConstants.ACCOUNT_EMAIL));
        assertEquals("EDITOR", json.get(DataConstants.ACCOUNT_ROLE));
        assertEquals(true, json.get(DataConstants.EDITOR_ADMIN));
    }

    @Test
    public void getQuestionJSON_createsCorrectJSONObject() {
        UUID questionID = UUID.randomUUID();
        UUID authorID = UUID.randomUUID();

        ArrayList<Category> categories = new ArrayList<>();
        categories.add(Category.CLASS);
        ArrayList<Language> languages = new ArrayList<>();
        languages.add(Language.JAVA);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(Course.CSCE_145);
        QuestionTag tag = new QuestionTag(categories, languages, courses);

        ArrayList<Segment> segments = new ArrayList<>();
        segments.add(new Segment("Description", "Problem description", DataType.STRING, "Solve this problem"));
        ArrayList<Segment> hints = new ArrayList<>();
        hints.add(new Segment("Hint 1", "First hint", DataType.STRING, "Try this approach"));
        ArrayList<Solution> solutions = new ArrayList<>();
        ArrayList<Comment> comments = new ArrayList<>();

        Question question = new Question(questionID, authorID, "Test Question", "2023-01-01", 4.5f, 10, -1, Difficulty.BEGINNER, segments, tag, hints, solutions, comments);

        JSONObject json = DataWriter.getQuestionJSON(question);

        assertEquals(questionID.toString(), json.get(DataConstants.QUESTION_ID));
        assertEquals(authorID.toString(), json.get(DataConstants.AUTHOR_ID));
        assertEquals("Test Question", json.get(DataConstants.QUESTION_TITLE));
        assertEquals(4.5f, json.get(DataConstants.QUESTION_RATING));
        assertEquals(10, json.get(DataConstants.QUESTION_TOTAL_RATINGS));
        assertEquals("BEGINNER", json.get(DataConstants.QUESTION_DIFFICULTY));

        JSONObject questionTagJson = (JSONObject) json.get(DataConstants.QUESTION_QUESTION_TAG);
        JSONArray categoriesJson = (JSONArray) questionTagJson.get(DataConstants.QUESTION_TAG_CATEGORY);
        assertEquals("CLASS", categoriesJson.get(0));
        JSONArray languagesJson = (JSONArray) questionTagJson.get(DataConstants.QUESTION_TAG_LANGUAGE);
        assertEquals("JAVA", languagesJson.get(0));
        JSONArray coursesJson = (JSONArray) questionTagJson.get(DataConstants.QUESTION_TAG_COURSE);
        assertEquals("CSCE_145", coursesJson.get(0));
    }

    @Test
    public void getComments_emptyList_returnsEmptyJSONArray() {
        ArrayList<Comment> comments = new ArrayList<>();
        JSONArray json = DataWriter.getComments(comments);
        assertTrue(json.isEmpty());
    }

    @Test
    public void getComments_withComments_createsCorrectJSONArray() {
        ArrayList<Comment> comments = new ArrayList<>();
        UUID accountID = UUID.randomUUID();
        Comment comment = new Comment("Test comment", accountID);
        comment.setDatePosted("2023-01-01");
        comment.like();
        comments.add(comment);

        JSONArray json = DataWriter.getComments(comments);

        assertEquals(1, json.size());
        JSONObject commentJson = (JSONObject) json.get(0);
        assertEquals(accountID.toString(), commentJson.get(DataConstants.COMMENT_ACCOUNT_ID));
        assertEquals("Test comment", commentJson.get(DataConstants.COMMENT_TEXT));
        assertEquals("2023-01-01", commentJson.get(DataConstants.COMMENT_DATE_POSTED));
        assertEquals(1, commentJson.get(DataConstants.COMMENT_LIKES));
    }

    @Test
    public void getComments_withReplies_createsNestedJSONArray() {
        ArrayList<Comment> comments = new ArrayList<>();
        UUID accountID = UUID.randomUUID();
        Comment comment = new Comment("Test comment", accountID);
        UUID replyID = UUID.randomUUID();
        comment.addReply(replyID, "Reply text");
        comments.add(comment);

        JSONArray json = DataWriter.getComments(comments);

        assertEquals(1, json.size());
        JSONObject commentJson = (JSONObject) json.get(0);
        JSONArray repliesJson = (JSONArray) commentJson.get(DataConstants.COMMENT_REPLIES);
        assertEquals(1, repliesJson.size());
        JSONObject replyJson = (JSONObject) repliesJson.get(0);
        assertEquals(replyID.toString(), replyJson.get(DataConstants.COMMENT_ACCOUNT_ID));
        assertEquals("Reply text", replyJson.get(DataConstants.COMMENT_TEXT));
    }
}