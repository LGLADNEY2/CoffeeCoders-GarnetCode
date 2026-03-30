package com.model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.UUID;


class QuestionFacadeTest {
    private QuestionFacade facade;
    private UUID questionIDTest;
    private Account accountTest;
    private Student studentTest;

    @BeforeEach
    public void setUp(){
        facade = new QuestionFacade();
        facade.addAccount("Ballister", "Blackheart", "BBH2012", "Passw0rd@123", "BBH2012@Institution.org", Role.STUDENT);
        boolean loggedIn = facade.login("BBH2012", "Passw0rd@123");
        assertTrue(loggedIn, "Setup Fail, Login Failed");
        accountTest = facade.debug_getAccount();

        //question setup from older driver
        String title = "Longest Subarray with Given Sum";
        Difficulty difficulty = Difficulty.INTERMEDIATE;
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(Category.CLASS); // Use CLASS as a generic category
        ArrayList<Language> languages = new ArrayList<>();
        languages.add(Language.JAVA);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(Course.CSCE_247);
        QuestionTag tag = new QuestionTag(categories, languages, courses);
        ArrayList<Segment> segments = new ArrayList<>();
        segments.add(new Segment(
                "Problem Statement",
                "Given an integer array nums and an integer sum k, return the length of the longest contiguous subarray whose total equals k. The array can contain negative numbers.\nExample 1: nums = [1, -1, 5, -2, 3], k = 3 → Output: 4. Example 2: nums = [-2, -1, 2, 1], k = 3 → Output: 2.",
                DataType.STRING,
                ""
        ));
        segments.add(new Segment(
                "Follow-up Questions",
                "- What is the time complexity of your algorithm?\n- Can you find a way to make your algorithm faster?",
                DataType.STRING,
                ""
        ));
        ArrayList<Segment> hints = new ArrayList<>();
        hints.add(new Segment("Hint 1", "Try every possible subarray and compute its sum.", DataType.HINT, ""));
        hints.add(new Segment("Hint 2", "Can you use a HashMap to optimize?", DataType.HINT, ""));
        int recommendedTime = 20;
        Question question = facade.addQuestion(title, difficulty, tag, segments, hints, recommendedTime);
        questionIDTest = question.getQuestionID();
        studentTest = (Student) facade.debug_getAccount();
        studentTest.addFavoriteQuestion(questionIDTest);
        studentTest.addTrustedRole(tag);
    }



    @Test
    public void findQuestions_keywordMatchSuccessful() {
        ArrayList <Question> check = facade.findQuestions("Sum");
        assertFalse(check.isEmpty(), "Keyword Match should have returned something.");
        assertTrue(check.get(0).getTitle().contains("Sum"), "Keyword Match should return something with Sum in it.");
    }
    @Test
    public void findQuestions_keywordMatchFail() {
        ArrayList <Question> check = facade.findQuestions("Example");
        assertTrue(check.isEmpty(), "Keyword Match shouldn't return with an invalid match.");
    }

    @Test
    public void findQuestions_tagMatchSuccessful() {
        ArrayList<Course> course_tag = new ArrayList<>();
        course_tag.add(Course.CSCE_247);
        QuestionTag example_tags = new QuestionTag(new ArrayList<>(), new ArrayList<>(), course_tag);
        ArrayList <Question> check = facade.findQuestions(example_tags);
        assertFalse(check.isEmpty(), "Keyword Match should have returned something.");
        assertEquals("Longest Subarray with Given Sum", check.get(0).getTitle());
    }

    @Test
    public void findQuestions_tagMatchFail() {
        ArrayList<Course> course_tag = new ArrayList<>();
        course_tag.add(Course.CSCE_146);
        QuestionTag example_tags = new QuestionTag(new ArrayList<>(), new ArrayList<>(), course_tag);
        ArrayList <Question> check = facade.findQuestions(example_tags);
        assertTrue(check.isEmpty(), "Keyword Match shouldn't return with an invalid match.");
    }

    @Test
    public void getQuestion_UUIDExists() {
        assertEquals(questionIDTest, facade.getQuestion(questionIDTest).getQuestionID());
    }

    @Test
    public void getQuestion_UUIDFail() {
        UUID wrongUUID = UUID.fromString("a7658164-21a9-4f5e-a253-c331a9a783e7");
        Question check = facade.getQuestion(wrongUUID);
        assertNull(check, "getQuestion should return null, set UUID shouldn't match unless absurdly lucky.");
    }

    @Test
    public void addQuestion_Normal() {
        String title = "Question Test 1";
        Difficulty difficulty = Difficulty.BEGINNER;
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(Category.CLASS); // Use CLASS as a generic category
        ArrayList<Language> languages = new ArrayList<>();
        languages.add(Language.JAVA);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(Course.CSCE_145);
        QuestionTag tag = new QuestionTag(categories, languages, courses);
        ArrayList<Segment> segments = new ArrayList<>();
        segments.add(new Segment(
                "Segment 1",
                "Segment Desc 1",
                DataType.STRING,
                ""
        ));
        segments.add(new Segment(
                "Segment 2",
                "Segment Desc 2",
                DataType.STRING,
                ""
        ));
        ArrayList<Segment> hints = new ArrayList<>();
        hints.add(new Segment("Hint 1", "Example Desc 1.", DataType.HINT, ""));
        hints.add(new Segment("Hint 2", "Example Desc 2", DataType.HINT, ""));
        Question question = facade.addQuestion(title, difficulty, tag, segments, hints);

        assertFalse(question.getTitle().isEmpty(), "Should return a title.");
        assertEquals("Question Test 1", question.getTitle());
    }

    @Test
    public void addQuestion_RecTime() {
        String title = "Question Test 2";
        Difficulty difficulty = Difficulty.BEGINNER;
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(Category.CLASS); // Use CLASS as a generic category
        ArrayList<Language> languages = new ArrayList<>();
        languages.add(Language.JAVA);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(Course.CSCE_145);
        QuestionTag tag = new QuestionTag(categories, languages, courses);
        ArrayList<Segment> segments = new ArrayList<>();
        segments.add(new Segment(
                "Segment 1",
                "Segment Desc 1",
                DataType.STRING,
                ""
        ));
        segments.add(new Segment(
                "Segment 2",
                "Segment Desc 2",
                DataType.STRING,
                ""
        ));
        ArrayList<Segment> hints = new ArrayList<>();
        hints.add(new Segment("Hint 1", "Example Desc 1.", DataType.HINT, ""));
        hints.add(new Segment("Hint 2", "Example Desc 2", DataType.HINT, ""));
        int recTime = 25;
        Question question = facade.addQuestion(title, difficulty, tag, segments, hints, recTime);

        assertFalse(question.getTitle().isEmpty(), "Should return a title.");
        assertEquals("Question Test 2", question.getTitle());
    }

    @Test
    public void editQuestion_test() {
        Question currentQuestion = facade.getQuestion(questionIDTest);
        facade.editQuestion(Difficulty.BEGINNER, currentQuestion.getQuestionTag(), currentQuestion.getSegments());
        assertEquals(Difficulty.BEGINNER, facade.getQuestion(questionIDTest).getDifficulty());
    }

    @Test
    public void removeQuestion_test() {
        assertTrue(facade.removeQuestion(), "Should return True if question is removed.");
    }

    @Test
    public void comment_submitTest() {
        assertTrue(facade.Comment("Comment Test"), "Should return True if comment is posted.");
    }

    @Test
    public void getFavoriteQuestions_test() {
        assertFalse(facade.getFavoriteQuestions().isEmpty(),"Should return something.");
    }

    @Test
    public void giveFeedback_submitTest(){
        facade.giveFeedback(3);
        assertEquals(3, facade.getQuestion(questionIDTest).getRating());
    }

    @Test
    public void submitQuestion_hasPermissions() {
        String title = "Question Test 1";
        Difficulty difficulty = Difficulty.BEGINNER;
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(Category.CLASS); // Use CLASS as a generic category
        ArrayList<Language> languages = new ArrayList<>();
        languages.add(Language.JAVA);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(Course.CSCE_247);
        QuestionTag tag = new QuestionTag(categories, languages, courses);
        ArrayList<Segment> segments = new ArrayList<>();
        segments.add(new Segment(
                "Segment 1",
                "Segment Desc 1",
                DataType.STRING,
                ""
        ));
        segments.add(new Segment(
                "Segment 2",
                "Segment Desc 2",
                DataType.STRING,
                ""
        ));
        ArrayList<Segment> hints = new ArrayList<>();
        hints.add(new Segment("Hint 1", "Example Desc 1.", DataType.HINT, ""));
        hints.add(new Segment("Hint 2", "Example Desc 2", DataType.HINT, ""));
        Question question = facade.addQuestion(title, difficulty, tag, segments, hints);

        ArrayList<QuestionTag> roles = new ArrayList<>();
        roles.add(tag);

        assertTrue(facade.submitQuestion(roles, question), "Should return True, as roles match.");
    }

    @Test
    public void submitQuestion_notPermitted() {
        String title = "Question Test 1";
        Difficulty difficulty = Difficulty.BEGINNER;
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(Category.CLASS); // Use CLASS as a generic category
        ArrayList<Language> languages = new ArrayList<>();
        languages.add(Language.JAVA);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(Course.CSCE_145);
        QuestionTag tag = new QuestionTag(categories, languages, courses);
        ArrayList<Segment> segments = new ArrayList<>();
        segments.add(new Segment(
                "Segment 1",
                "Segment Desc 1",
                DataType.STRING,
                ""
        ));
        segments.add(new Segment(
                "Segment 2",
                "Segment Desc 2",
                DataType.STRING,
                ""
        ));
        ArrayList<Segment> hints = new ArrayList<>();
        hints.add(new Segment("Hint 1", "Example Desc 1.", DataType.HINT, ""));
        hints.add(new Segment("Hint 2", "Example Desc 2", DataType.HINT, ""));
        Question question = facade.addQuestion(title, difficulty, tag, segments, hints);

        assertFalse(facade.submitQuestion(studentTest.getTrustedRoles(), question), "Should return False, as roles do not match");
    }

    @Test
    public void submitSolution_Test() {
        ArrayList<Segment> segments = new ArrayList<>();
        segments.add(new Segment(
                "Segment 1",
                "Segment Desc 1",
                DataType.STRING,
                ""
        ));
        assertTrue(facade.submitSolution(Language.JAVA, "Solution 1", segments), "Should return True if solution submits.");

    }

    @Test
    public void addAccount_successful() {
        assertTrue(facade.addAccount("Ambrosius", "Goldenloin", "GoldenBoy", "P4ssword@234", "Goldenboy@institution.org"), "Should return true, since account would be unique");
    }

    @Test
    public void addAccount_duplicateFail() {
        assertFalse(facade.addAccount("Ballister", "Boldheart", "BBH2012", "Pas5word@345", "BBH2012@institution.org"), "Should return False, as username is already in use");

    }

    @Test
    public void editAccount_test() {
        facade.editAccount(accountTest.getFirstName(), "Boldheart", "BBH2023", accountTest.getPassword(), accountTest.getEmail());
        assertEquals("BBH2023", facade.debug_getAccount().getUsername());
    }

    @Test
    public void removeAccount_test() {
        facade.removeAccount(accountTest.getAccountID());
        accountTest = facade.debug_getAccount();
        assertNull(accountTest.getAccountID(), "Should return Null, as no account exists.");
    }

    @Test
    public void updateDailyStreak_test() {
        facade.updateDailyStreak();
        assertEquals(2, facade.getDailyStreak());
    }

    @Test
    public void getDailyStreak_test() {
        facade.getDailyStreak();
        assertEquals(1, facade.getDailyStreak());
    }

    @Test
    public void login_validCredentials() {
        assertTrue(facade.login("BBH2012", "Passw0rd@123"), "Should return true, as account credentials are correct");
    }

    @Test
    public void login_invalidCredentials() {
        assertFalse(facade.login("BBH2012", "WrongPassword@123"), "Should return false, as account credentials are incorrect");
    }

    @Test
    public void logout_test() {
        assertTrue(facade.logout(), "Should return True if account logout works.");
    }




}
