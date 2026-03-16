package com.model;

public class Driver {
    private QuestionFacade qFacade;

    Driver() {
        qFacade = new QuestionFacade();
    }

    public void run() {
        scenario1();
        scenario2();
        scenario3();
    }

    public void scenario1() {
        System.out.println();

		if (!qFacade.login("EJohnson", "Lightn@52ing")) {
			System.out.println("Sorry we couldn't login.");
			return;
		}

		System.out.println("Amy Smith is now logged in");
    }

    public void scenario2() {
        System.out.println();

		if (!qFacade.login("OSmith", "HaMMer@STing223")) {
			System.out.println("Sorry we couldn't login.");
			return;
		}
		System.out.println("Bobby Smith is now logged in");
    }
    public void scenario3() {
        if(!qFacade.save()) {
            System.out.println("Sorry, we couldn't save.");
        }
        qFacade.logout();
    }

    public static void main(String[] args) {
        Driver driver = new Driver();
        driver.run();
    }

}
