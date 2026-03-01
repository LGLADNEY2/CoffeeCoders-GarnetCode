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
    
    public void setCateogry(ArrayList<Category> category) {

    }

    public void setLanguage(ArrayList<Language> language) {

    }

    public void setCourse(ArrayList<Course> course) {
        
    }
}
