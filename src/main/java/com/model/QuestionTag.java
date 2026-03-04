package com.model;

import java.util.ArrayList;

public class QuestionTag {
    private ArrayList<Category> category;
    private ArrayList<Language> language;
    private ArrayList<Course> course;

    public QuestionTag(ArrayList<Category> category, ArrayList<Language> language, ArrayList<Course> course) {
        this.category = category;
        this.language = language;
        this.course = course;
    }

    public ArrayList<Category> getCategory() {return category;}
    public ArrayList<Language> getLanguage() {return language;}
    public ArrayList<Course> getCourse() {return course;}
    
    public void setCategory(ArrayList<Category> category) {
        this.category = category;
    }

    public void setLanguage(ArrayList<Language> language) {
        this.language = language;
    }

    public void setCourse(ArrayList<Course> course) {
        this.course = course;
    }
}
