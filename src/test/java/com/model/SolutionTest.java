package com.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.UUID;

public class SolutionTest {

    @Test
    public void constructorWithRequiredFields_setsFieldsCorrectly() {
        UUID authorID = UUID.randomUUID();
        ArrayList<Segment> segments = new ArrayList<>();
        segments.add(new Segment("Code", "Solution code", DataType.CODE, "public class Test {}"));

        Solution solution = new Solution(authorID, "Test Solution", Language.JAVA, segments);

        assertEquals(authorID, solution.getAuthorID());
        assertEquals("Test Solution", solution.getTitle());
        assertEquals(Language.JAVA, solution.getLanguage());
        assertEquals(segments, solution.getSegments());
        assertEquals(0, solution.getLikes());
        assertNotNull(solution.getComments());
        assertTrue(solution.getComments().isEmpty());
    }

    @Test
    public void constructorWithAllFields_setsAllFieldsCorrectly() {
        UUID authorID = UUID.randomUUID();
        ArrayList<Segment> segments = new ArrayList<>();
        segments.add(new Segment("Code", "Solution code", DataType.CODE, "code"));
        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(new Comment("Comment", UUID.randomUUID()));

        Solution solution = new Solution(authorID, "Title", Language.PYTHON, segments, comments, 5);

        assertEquals(authorID, solution.getAuthorID());
        assertEquals("Title", solution.getTitle());
        assertEquals(Language.PYTHON, solution.getLanguage());
        assertEquals(segments, solution.getSegments());
        assertEquals(comments, solution.getComments());
        assertEquals(5, solution.getLikes());
    }

    @Test
    public void getAuthorID_returnsAuthorID() {
        UUID authorID = UUID.randomUUID();
        Solution solution = new Solution(authorID, "Title", Language.JAVA, new ArrayList<>());
        assertEquals(authorID, solution.getAuthorID());
    }

    @Test
    public void getTitle_returnsTitle() {
        Solution solution = new Solution(UUID.randomUUID(), "Test Title", Language.JAVA, new ArrayList<>());
        assertEquals("Test Title", solution.getTitle());
    }

    @Test
    public void getLanguage_returnsLanguage() {
        Solution solution = new Solution(UUID.randomUUID(), "Title", Language.CPP, new ArrayList<>());
        assertEquals(Language.CPP, solution.getLanguage());
    }

    @Test
    public void getSegments_returnsSegments() {
        ArrayList<Segment> segments = new ArrayList<>();
        segments.add(new Segment("Test", "Desc", DataType.STRING, "Data"));
        Solution solution = new Solution(UUID.randomUUID(), "Title", Language.JAVA, segments);
        assertEquals(segments, solution.getSegments());
    }

    @Test
    public void getComments_returnsComments() {
        Solution solution = new Solution(UUID.randomUUID(), "Title", Language.JAVA, new ArrayList<>());
        assertNotNull(solution.getComments());
        assertTrue(solution.getComments().isEmpty());
    }

    @Test
    public void getLikes_returnsLikes() {
        Solution solution = new Solution(UUID.randomUUID(), "Title", Language.JAVA, new ArrayList<>());
        assertEquals(0, solution.getLikes());
    }

    @Test
    public void setAuthorID_updatesAuthorID() {
        Solution solution = new Solution(UUID.randomUUID(), "Title", Language.JAVA, new ArrayList<>());
        UUID newID = UUID.randomUUID();
        solution.setAuthorID(newID);
        assertEquals(newID, solution.getAuthorID());
    }

    @Test
    public void setTitle_updatesTitle() {
        Solution solution = new Solution(UUID.randomUUID(), "Old", Language.JAVA, new ArrayList<>());
        solution.setTitle("New Title");
        assertEquals("New Title", solution.getTitle());
    }

    @Test
    public void setLanguage_updatesLanguage() {
        Solution solution = new Solution(UUID.randomUUID(), "Title", Language.JAVA, new ArrayList<>());
        solution.setLanguage(Language.PYTHON);
        assertEquals(Language.PYTHON, solution.getLanguage());
    }

    @Test
    public void setSegments_updatesSegments() {
        Solution solution = new Solution(UUID.randomUUID(), "Title", Language.JAVA, new ArrayList<>());
        ArrayList<Segment> newSegments = new ArrayList<>();
        newSegments.add(new Segment("New", "Desc", DataType.STRING, "Data"));
        solution.setSegments(newSegments);
        assertEquals(newSegments, solution.getSegments());
    }

    @Test
    public void like_increasesLikeCount() {
        Solution solution = new Solution(UUID.randomUUID(), "Title", Language.JAVA, new ArrayList<>());
        assertEquals(0, solution.getLikes());
        solution.like();
        assertEquals(1, solution.getLikes());
        solution.like();
        assertEquals(2, solution.getLikes());
    }

    @Test
    public void addComment_validInputs_addsCommentAndReturnsTrue() {
        Solution solution = new Solution(UUID.randomUUID(), "Title", Language.JAVA, new ArrayList<>());
        UUID commentAuthor = UUID.randomUUID();
        boolean result = solution.addComment("Test comment", commentAuthor);
        assertTrue(result);
        assertEquals(1, solution.getComments().size());
        assertEquals("Test comment", solution.getComments().get(0).getText());
        assertEquals(commentAuthor, solution.getComments().get(0).getAccountID());
    }

    @Test
    public void addComment_nullText_returnsFalse() {
        Solution solution = new Solution(UUID.randomUUID(), "Title", Language.JAVA, new ArrayList<>());
        boolean result = solution.addComment(null, UUID.randomUUID());
        assertFalse(result);
        assertTrue(solution.getComments().isEmpty());
    }

    @Test
    public void addComment_emptyText_returnsFalse() {
        Solution solution = new Solution(UUID.randomUUID(), "Title", Language.JAVA, new ArrayList<>());
        boolean result = solution.addComment("", UUID.randomUUID());
        assertFalse(result);
        assertTrue(solution.getComments().isEmpty());
    }

    @Test
    public void addComment_nullAuthorID_returnsFalse() {
        Solution solution = new Solution(UUID.randomUUID(), "Title", Language.JAVA, new ArrayList<>());
        boolean result = solution.addComment("Comment", null);
        assertFalse(result);
        assertTrue(solution.getComments().isEmpty());
    }

    @Test
    public void removeComment_returnsTrue() {
        // This is a stub method that always returns true
        Solution solution = new Solution(UUID.randomUUID(), "Title", Language.JAVA, new ArrayList<>());
        boolean result = solution.removeComment(UUID.randomUUID(), "2023-01-01");
        assertTrue(result);
    }

    @Test
    public void editTitle_validTitle_updatesTitleAndReturnsTrue() {
        Solution solution = new Solution(UUID.randomUUID(), "Old", Language.JAVA, new ArrayList<>());
        boolean result = solution.editTitle("New Title");
        assertTrue(result);
        assertEquals("New Title", solution.getTitle());
    }

    @Test
    public void editTitle_nullTitle_returnsFalse() {
        Solution solution = new Solution(UUID.randomUUID(), "Old", Language.JAVA, new ArrayList<>());
        boolean result = solution.editTitle(null);
        assertFalse(result);
        assertEquals("Old", solution.getTitle());
    }

    @Test
    public void editTitle_emptyTitle_returnsFalse() {
        Solution solution = new Solution(UUID.randomUUID(), "Old", Language.JAVA, new ArrayList<>());
        boolean result = solution.editTitle("");
        assertFalse(result);
        assertEquals("Old", solution.getTitle());
    }

    @Test
    public void editSolution_validSegments_updatesSegmentsAndReturnsTrue() {
        Solution solution = new Solution(UUID.randomUUID(), "Title", Language.JAVA, new ArrayList<>());
        ArrayList<Segment> newSegments = new ArrayList<>();
        newSegments.add(new Segment("New", "Desc", DataType.STRING, "Data"));
        boolean result = solution.editSolution(newSegments);
        assertTrue(result);
        assertEquals(newSegments, solution.getSegments());
    }

    @Test
    public void editSolution_nullSegments_returnsFalse() {
        Solution solution = new Solution(UUID.randomUUID(), "Title", Language.JAVA, new ArrayList<>());
        boolean result = solution.editSolution(null);
        assertFalse(result);
    }

    @Test
    public void editSolution_emptySegments_returnsFalse() {
        Solution solution = new Solution(UUID.randomUUID(), "Title", Language.JAVA, new ArrayList<>());
        boolean result = solution.editSolution(new ArrayList<>());
        assertFalse(result);
    }
}