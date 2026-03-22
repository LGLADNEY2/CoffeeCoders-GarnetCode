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

    qFacade.removeAccount("SSparrow");
    qFacade.removeAccount("SallySparrow");

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
        ArrayList<Question> questions = QuestionList.getInstance().getQuestions();
        Question dailyChallenge = null;

        for (Question question : questions) {
            if (question.getTitle().equalsIgnoreCase("Binary Search Tree")) {
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

