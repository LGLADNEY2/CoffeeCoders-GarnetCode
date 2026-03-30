package com.model;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class testQuestion {

    @Test
    public void testRating() {
        UUID id = UUID.randomUUID();
        Question question = new Question(id, "test", null, null, null, null);
        boolean noRatings = false;
        if(question.getRating() == 0)
            noRatings = true;
        assertTrue(noRatings);
    }

    @Test
    public void testAddRating() {
        UUID id = UUID.randomUUID();
        Question question = new Question(id, "test", null, null, null, null);
        boolean testAddRating = question.addRating(5);
        assertTrue(testAddRating);
    }

    @Test
    public void testAddRatingInvalidHigh() {
        UUID id = UUID.randomUUID();
        Question question = new Question(id, "test", null, null, null, null);
        boolean testAddRating = question.addRating(6);
        assertFalse(testAddRating);
    }

    @Test
    public void testAddRatingInvalidLow() {
        UUID id = UUID.randomUUID();
        Question question = new Question(id, "test", null, null, null, null);
        boolean testAddRating = question.addRating(-1);
        assertFalse(testAddRating);
    }
    
    @Test
    public void testAddRatingZero() {
        UUID id = UUID.randomUUID();
        Question question = new Question(id, "test", null, null, null, null);
        boolean testAddRating = question.addRating(0);
        assertTrue(testAddRating);
    }

    @Test
    public void testTotalRating() {
        UUID id = UUID.randomUUID();
        Question question = new Question(id, "test", null, null, null, null);
        boolean noRatings = question.getTotalRatings() == 0;
        assertTrue(noRatings);
    }

    @Test
    public void testAddTwoRatings() {
        UUID id = UUID.randomUUID();
        Question question = new Question(id, "test", null, null, null, null);
        question.addRating(5);
        question.addRating(5);
        boolean twoRatings = question.getTotalRatings() == 2;
        assertTrue(twoRatings);
    }

    @Test
    public void testCalculateRatings() {
        UUID id = UUID.randomUUID();
        Question question = new Question(id, "test", null, null, null, null);
        question.addRating(5);
        question.addRating(4);
        boolean calculateRatings = question.getRating() == (5+4)/2;
        assertTrue(calculateRatings);
    }

    @Test
    public void testQuestionComment() {
        UUID questionID = UUID.randomUUID();
        Question question = new Question(questionID, "test", null, null, null, null);
        UUID accountID = UUID.randomUUID();
        Account account = new Account("User", "123");
        boolean commentAdded = question.addComment("hello", accountID);
        assertTrue(commentAdded);
    }

    @Test
    public void testQuestionCommentNullID() {
        UUID questionID = UUID.randomUUID();
        Question question = new Question(questionID, "test", null, null, null, null);
        boolean commentAdded = question.addComment("hello", null);
        assertFalse(commentAdded);
    }   

    @Test
    public void testQuestionCommentNullText() {
        UUID questionID = UUID.randomUUID();
        Question question = new Question(questionID, "test", null, null, null, null);
        UUID accountID = UUID.randomUUID();
        Account account = new Account("User", "123");
        boolean commentAdded = question.addComment(null, accountID);
        assertFalse(commentAdded);
    }   

    @Test
    public void testRemoveComment() {
        UUID questionID = UUID.randomUUID();
        Question question = new Question(questionID, "test", null, null, null, null);
        UUID accountID = UUID.randomUUID();
        Account account = new Account("User", "123");
        question.addComment("hello", accountID);
        boolean commentRemoved = false;
        // No way to find a comment's date after its been created
        assertTrue(commentRemoved);  
    }

    @Test
    public void testCommentArrayListAfterRemoval() {
        UUID questionID = UUID.randomUUID();
        Question question = new Question(questionID, "test", null, null, null, null);
        UUID accountID = UUID.randomUUID();
        Account account = new Account("User", "123");
        question.addComment("hello", accountID);
        question.addComment("hello", accountID);
        boolean arrayIntegrity = true;
        if(question.getComments().get(0) == null) {arrayIntegrity = false;}
        assertTrue(arrayIntegrity); 
    }

    @Test
    public void testQuestionSolution() {
        UUID questionID = UUID.randomUUID();
        Question question = new Question(questionID, "test", null, null, null, null);
        UUID accountID = UUID.randomUUID();
        Account account = new Account("User", "123");
        ArrayList<Segment> segments = new ArrayList<>();
        segments.add(new Segment("1", "1", DataType.STRING, null));
        question.addSolution(accountID, "title", Language.CPP, segments);
        boolean solutionMade = false;
        if(question.getSolutions().get(0).getAuthorID() == accountID) {solutionMade = true;}
        assertTrue(solutionMade);
    }
}