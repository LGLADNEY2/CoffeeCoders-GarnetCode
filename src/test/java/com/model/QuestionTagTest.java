package com.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class QuestionTagTest {

    @Test
    public void constructor_setsFieldsCorrectly() {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(Category.CLASS);
        categories.add(Category.INTERVIEW);

        ArrayList<Language> languages = new ArrayList<>();
        languages.add(Language.JAVA);
        languages.add(Language.PYTHON);

        ArrayList<Course> courses = new ArrayList<>();
        courses.add(Course.CSCE_145);
        courses.add(Course.CSCE_146);

        QuestionTag tag = new QuestionTag(categories, languages, courses);

        assertEquals(categories, tag.getCategory());
        assertEquals(languages, tag.getLanguage());
        assertEquals(courses, tag.getCourse());
    }

    @Test
    public void getCategory_returnsCategoryList() {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(Category.CLASS);
        QuestionTag tag = new QuestionTag(categories, new ArrayList<>(), new ArrayList<>());

        assertEquals(categories, tag.getCategory());
        assertEquals(1, tag.getCategory().size());
        assertEquals(Category.CLASS, tag.getCategory().get(0));
    }

    @Test
    public void getLanguage_returnsLanguageList() {
        ArrayList<Language> languages = new ArrayList<>();
        languages.add(Language.JAVA);
        QuestionTag tag = new QuestionTag(new ArrayList<>(), languages, new ArrayList<>());

        assertEquals(languages, tag.getLanguage());
        assertEquals(1, tag.getLanguage().size());
        assertEquals(Language.JAVA, tag.getLanguage().get(0));
    }

    @Test
    public void getCourse_returnsCourseList() {
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(Course.CSCE_145);
        QuestionTag tag = new QuestionTag(new ArrayList<>(), new ArrayList<>(), courses);

        assertEquals(courses, tag.getCourse());
        assertEquals(1, tag.getCourse().size());
        assertEquals(Course.CSCE_145, tag.getCourse().get(0));
    }

    @Test
    public void setCategory_updatesCategoryList() {
        QuestionTag tag = new QuestionTag(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        ArrayList<Category> newCategories = new ArrayList<>();
        newCategories.add(Category.INTERVIEW);

        tag.setCategory(newCategories);

        assertEquals(newCategories, tag.getCategory());
        assertEquals(1, tag.getCategory().size());
        assertEquals(Category.INTERVIEW, tag.getCategory().get(0));
    }

    @Test
    public void setLanguage_updatesLanguageList() {
        QuestionTag tag = new QuestionTag(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        ArrayList<Language> newLanguages = new ArrayList<>();
        newLanguages.add(Language.CPP);

        tag.setLanguage(newLanguages);

        assertEquals(newLanguages, tag.getLanguage());
        assertEquals(1, tag.getLanguage().size());
        assertEquals(Language.CPP, tag.getLanguage().get(0));
    }

    @Test
    public void setCourse_updatesCourseList() {
        QuestionTag tag = new QuestionTag(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        ArrayList<Course> newCourses = new ArrayList<>();
        newCourses.add(Course.CSCE_211);

        tag.setCourse(newCourses);

        assertEquals(newCourses, tag.getCourse());
        assertEquals(1, tag.getCourse().size());
        assertEquals(Course.CSCE_211, tag.getCourse().get(0));
    }

    @Test
    public void constructor_withEmptyLists_createsEmptyTag() {
        QuestionTag tag = new QuestionTag(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        assertNotNull(tag.getCategory());
        assertNotNull(tag.getLanguage());
        assertNotNull(tag.getCourse());
        assertTrue(tag.getCategory().isEmpty());
        assertTrue(tag.getLanguage().isEmpty());
        assertTrue(tag.getCourse().isEmpty());
    }
}