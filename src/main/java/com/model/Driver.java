package com.model;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Driver {
    private QuestionFacade qFacade;

    public Driver() {
        qFacade = new QuestionFacade();
    }

    public void run() {
        scenario1();
        scenario2();
        scenario3();
        scenario4();
        scenario5();
        scenario6();
    }

    public void scenario1() {
        System.out.println();

		if (!qFacade.login("EJohnson", "Lightn@52ing")) {
			System.out.println("Sorry we couldn't login.");
			return;
		}

		System.out.println("Emma Johnson is now logged in");
        qFacade.save();
        qFacade.logout();
        System.out.println("Emma Johnson is now logged out");
        System.out.println();
    }

    public void scenario2() {
        System.out.println();

		if (!qFacade.login("OSmith", "HaMMer@STing223")) {
			System.out.println("Sorry we couldn't login.");
			return;
		}
		System.out.println("Oliver Smith is now logged in");
        qFacade.save();
        qFacade.logout();
        System.out.println("Oliver Smith is now logged out");  
        System.out.println();
    }

    //Invalid Login Attempt
    public void scenario3() {
        if (!qFacade.login("EJohnson", "WrongPassword")) {
            System.out.println("Invalid Username or Password.");
        return;
        }
    }

    // Create Account - Duplicate User
    public void scenario4() {
    System.out.println();
    System.out.println("Create Account - Duplicate User");

    Account sullivan = AccountList.getInstance().getAccount("SSparrow", "Birds@123");
    if (sullivan != null) {
        qFacade.removeAccount(sullivan.getAccountID());
}
    Account sally = AccountList.getInstance().getAccount("SallySparrow", "TimeL00p@42");
    if (sally != null) {
        qFacade.removeAccount(sally.getAccountID());
    }
    qFacade.addAccount("Sullivan", "Sparrow", "SSparrow", "Birds@123", "sullivan.sparrow@example.com");

    boolean duplicateRejected = !qFacade.addAccount(
        "Sally",
        "Sparrow",
        "SSparrow",
        "Birds@123",
        "sally.sparrow@example.com"
    );

    if (duplicateRejected) {
        System.out.println("Sally Sparrow could not create an account because those credentials already belong to Sullivan Sparrow.");
    } else {
        System.out.println("Duplicate account test failed.");
    }

    System.out.println();
    System.out.println("Create Account - Success");

    boolean accountCreated = qFacade.addAccount(
        "Sally",
        "Sparrow",
        "SallySparrow",
        "TimeL00p@42",
        "sally.sparrow@example.com",
        Role.EDITOR
    );

    if (!accountCreated) {
        System.out.println("Sally Sparrow's updated account could not be created.");
        return;
    }

    System.out.println("Sally Sparrow successfully created a Publisher account.");

    if (!qFacade.login("SallySparrow", "TimeL00p@42")) {
        System.out.println("Sally Sparrow could not log in.");
        return;
    }

    System.out.println("Sally Sparrow validly logged in as a Publisher.");
    qFacade.logout();
    System.out.println("Sally Sparrow is now logged out");
    System.out.println();
}

// Create a New Question with Two Solutions
    public void scenario5() {
        System.out.println();
        System.out.println("Sally Sparrow Creates a New Question and Two Solutions");
        // Login as Sally Sparrow
        if (!qFacade.login("SallySparrow", "TimeL00p@42")) {
            System.out.println("Sally Sparrow could not log in.");
            return;
        }

        // Prepare question details
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
        Question question = qFacade.addQuestion(title, difficulty, tag, segments, hints, recommendedTime);
        if (question == null) {
            System.out.println("Failed to create the question.");
            return;
        }

        // Add Solution 1: Brute Force
        ArrayList<Segment> sol1Segments = new ArrayList<>();
        sol1Segments.add(new Segment(
            "Brute Force Approach",
            "Try every possible subarray and compute its sum. Time Complexity: O(n^2). See code in docs/LongestSubarrayBruteForce.java.",
            DataType.FILE,
            "docs/LongestSubarrayBruteForce.java"
        ));
        sol1Segments.add(new Segment(
            "Brute Force Image",
            "See brute force code image.",
            DataType.IMAGE,
            "longestsubarray-bruteforce.png"
        ));
        qFacade.submitSolution(Language.JAVA, "Brute Force Solution", sol1Segments);

        // Add Solution 2: HashMap
        ArrayList<Segment> sol2Segments = new ArrayList<>();
        sol2Segments.add(new Segment(
            "HashMap Approach",
            "Use prefix sums and a HashMap to track sums. Time Complexity: O(n). See code in docs/LongestSubarrayHashMap.java.",
            DataType.FILE,
            "docs/LongestSubarrayHashMap.java"
        ));
        sol2Segments.add(new Segment(
            "HashMap Image",
            "See HashMap code image.",
            DataType.IMAGE,
            "longestsubarray-hashmap.png"
        ));
        qFacade.submitSolution(Language.JAVA, "HashMap Solution", sol2Segments);

        System.out.println("Question and solutions created by Sally Sparrow.");
        qFacade.logout();
        System.out.println("Sally Sparrow is now logged out");
        System.out.println();
    }

// Completes Daily Tasks and Maintains a Streak
    public void scenario6() {
        System.out.println();
        System.out.println("Jimmy Bauer Completes Daily Tasks and Maintains a Streak");
    // Checks to see if Jimmy can log in, if not prints an error message and exits the scenario
        if (!qFacade.login("JBauer", "L!obster67")) {
			System.out.println("Sorry we couldn't login.");
			return;
		}
        // Gets the student account for Jimmy Bauer and updates his daily streak with the current date, then prints out his new daily streak        
        Student jimmy = (Student) AccountList.getInstance().getAccount("JBauer", "L!obster67");
        jimmy.updateDailyStreak(new java.util.Date());
        System.out.println("Jimmy Bauer is now logged in");
        System.out.println("Jimmy Bauer's current daily streak: " + jimmy.getDailyStreak());

        // Finds the question with the title "Binary Search Tree" and sets it as the daily challenge for Jimmy to complete
        ArrayList<Question> allQuestions = QuestionList.getInstance().getQuestions();
        Question dailyChallenge = null;

        for (Question question : allQuestions) {
        if (question.getTitle() != null && question.getTitle().toLowerCase().contains("binary search tree")) {
            dailyChallenge = question;
            break;
        }
    }

        // If the question is not found, prints an error message  
        if (dailyChallenge == null) {
        System.out.println("No daily challenge question was found.");
         return;
}
        System.out.println("Jimmy Bauer's daily challenge: " + dailyChallenge.getTitle());

        // Prints out the segments of the daily challenge question for Jimmy to work through
        for (Segment segment : dailyChallenge.getSegments()) {
        System.out.println(segment.getTitle() + ": " + segment.getDesc());
        if (segment.getData() != null && !segment.getData().isBlank()) {
            System.out.println(segment.getData());
        }
        System.out.println();
}
    // After Jimmy completes the daily challenge, prints out the solutions for the question
    ArrayList<Solution> solutions = dailyChallenge.getSolutions();
    for (int i = 0; i < solutions.size(); i++) {
        Solution solution = solutions.get(i);
        System.out.println("Solution " + (i + 1) + ": " + solution.getTitle());

        for (Segment segment : solution.getSegments()) {
            System.out.println(segment.getTitle() + ": " + segment.getDesc());
            if (segment.getData() != null && !segment.getData().isBlank()) {
                System.out.println(segment.getData());
            }
        }
        System.out.println();
}
    // Adds a comment to the second solution for the question from Jimmy asking for an explanation of why the second solution works, then prints out the comment
    if (solutions.size() >= 2) {
        String jimmyComment = "Can someone explain why this second solution works?";
        solutions.get(1).addComment(jimmyComment, jimmy.getAccountID());
        System.out.println(jimmyComment);
}

    // Jimmy searches for Binary Search Tree questions
    ArrayList<Question> results = qFacade.findQuestions("Binary Search Tree");
    System.out.println("Binary Search Tree results: " + results.size());

    for (Question question : results) {
        System.out.println(question.getTitle() + " - " + question.getDifficulty());
    }

    jimmy.setDailyStreak(jimmy.getDailyStreak() + 1);
    System.out.println("Jimmy Bauer's daily streak is now: " + jimmy.getDailyStreak());

        qFacade.save();
        qFacade.logout();
        System.out.println("Jimmy Bauer is now logged out");
        System.out.println();


    }
    public static void main(String[] args) {
        Driver driver = new Driver();
        driver.run();
    }

}

