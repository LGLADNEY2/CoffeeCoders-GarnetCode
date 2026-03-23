package com.model;

import java.util.ArrayList;

/**
 * Represents category, language, and course tags associated with a question.
 * @author Coffee Coders
 */
public class QuestionTag {
    private ArrayList<Category> category;
    private ArrayList<Language> language;
    private ArrayList<Course> course;

    /**
     * Creates a question tag with category, language, and course values.
     *
     * @param category category tags
     * @param language language tags
     * @param course course tags
     */
    public QuestionTag(ArrayList<Category> category, ArrayList<Language> language, ArrayList<Course> course) {
        this.category = category;
        this.language = language;
        this.course = course;
    }

    /**
     * Returns the category tags.
     *
     * @return category list
     */
    public ArrayList<Category> getCategory() {return category;}

    /**
     * Returns the language tags.
     *
     * @return language list
     */
    public ArrayList<Language> getLanguage() {return language;}

    /**
     * Returns the course tags.
     *
     * @return course list
     */
    public ArrayList<Course> getCourse() {return course;}
    
    /**
     * Sets the category tags.
     *
     * @param category new category list
     */
    public void setCategory(ArrayList<Category> category) {
        this.category = category;
    }

    /**
     * Sets the language tags.
     *
     * @param language new language list
     */
    public void setLanguage(ArrayList<Language> language) {
        this.language = language;
    }

    /**
     * Sets the course tags.
     *
     * @param course new course list
     */
    public void setCourse(ArrayList<Course> course) {
        this.course = course;
    }
}
