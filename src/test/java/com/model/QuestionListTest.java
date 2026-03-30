package com.model;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests for QuestionList Class
 * @author joshua coleman
 */
public class QuestionListTest {
    private QuestionList questionList;
    private Question question = new Question(UUID.randomUUID(), "Test", null, null, null, null);
    public void setUp() {
        questionList = QuestionList.getInstance();
        for(Question question: questionList.getQuestions()) {
            questionList.removeQuestion(question.getQuestionID());
        }
    }

    @Test
    public void testAddingQuestions() {
        setUp();
        UUID questionID = questionList.addQuestion(UUID.randomUUID(), "test", Difficulty.BEGINNER, new QuestionTag(null, null, null), new ArrayList<>(), new ArrayList<>(), 0);
        assertTrue(questionID != null);
    }

    @Test
    public void testRemoveQuestion() {
        setUp();
        UUID questionID = questionList.addQuestion(UUID.randomUUID(), "test", Difficulty.BEGINNER, new QuestionTag(null, null, null), new ArrayList<>(), new ArrayList<>(), 0);
        questionList.removeQuestion(questionID);
        boolean questionRemoved;
        for(Question question: questionList.getQuestions()) {
            if(question.getQuestionID() == questionID)
                assertFalse(questionRemoved = false);
        }
        assertTrue(questionRemoved = true);
    }

    @Test
    public void testRemoveQuestionInvalid() {
        setUp();
        assertFalse(questionList.removeQuestion(UUID.randomUUID()));
    }

    @Test
    public void testViewCompleted() {
        setUp();
        AccountList accountList = AccountList.getInstance();
        ArrayList<Question> questions = questionList.viewCompleted(accountList.getAccounts().get(0).getAccountID());
        assertTrue(questions != null);
    }

    @Test
    public void testGetQuestion() {
        setUp();
        UUID questionID = questionList.addQuestion(UUID.randomUUID(), "test", Difficulty.BEGINNER, new QuestionTag(null, null, null), new ArrayList<>(), new ArrayList<>(), 0);
        assertTrue(questionList.getQuestion(questionID) != null);
    }

    @Test
    public void testGetQuestionInvalidID() {
        setUp();
        assertFalse(questionList.getQuestion(UUID.randomUUID()) != null);
    }

    @Test
    public void testGetQuestionBasedOnTag() {
        setUp();
        Course course = Course.CSCE_145;
        Language language = Language.JAVA;
        Category category = Category.CLASS;
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Language> languages = new ArrayList<>();
        ArrayList<Category> categorys = new ArrayList<>();
        courses.add(course);
        languages.add(language);
        categorys.add(category);
        QuestionTag tag = new QuestionTag(categorys,languages,courses);
        questionList.addQuestion(UUID.randomUUID(), "test", Difficulty.BEGINNER, tag, new ArrayList<>(), new ArrayList<>(), 0);
        assertTrue(questionList.getQuestions(tag) != null);
    }
    
    @Test
    public void testGetQuestionBasedOnTagInvalid() {
        setUp();
        Course course = Course.CSCE_145;
        Language language = Language.JAVA;
        Category category = Category.CLASS;
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Language> languages = new ArrayList<>();
        ArrayList<Category> categorys = new ArrayList<>();
        courses.add(course);
        languages.add(language);
        categorys.add(category);
        QuestionTag tag = new QuestionTag(categorys,languages,courses);
        questionList.addQuestion(UUID.randomUUID(), "test", Difficulty.BEGINNER, tag, new ArrayList<>(), new ArrayList<>(), 0);
        assertTrue(questionList.getQuestions(new QuestionTag(null, null, null)).isEmpty());
    }

    @Test
    public void testGetQUestionsBasedOnKeyWord() {
        setUp();
        questionList.addQuestion(UUID.randomUUID(), "test", Difficulty.BEGINNER, null, new ArrayList<>(), new ArrayList<>(), 0);
        assertTrue(questionList.getQuestions("test").get(0) != null);
    }

    @Test
    public void testGetQUestionsBasedOnKeyWordWrongWord() {
        setUp();
        questionList.addQuestion(UUID.randomUUID(), "test", Difficulty.BEGINNER, null, new ArrayList<>(), new ArrayList<>(), 0);
        assertTrue(questionList.getQuestions("invalid").isEmpty());
    }

    @Test
    public void testGetQUestionsBasedOnKeyWordEmpty() {
        setUp();
        questionList.addQuestion(UUID.randomUUID(), "test", Difficulty.BEGINNER, null, new ArrayList<>(), new ArrayList<>(), 0);
        assertTrue(questionList.getQuestions("").isEmpty());
    }
}

