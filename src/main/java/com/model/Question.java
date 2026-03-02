package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Question {
    private UUID questionID;
    private UUID authorID;
    private String title;
    private int rating;
    private String datePosted;
    private int recommendedTime;
    private Difficulty difficulty;
    private ArrayList<Segment> segments;
    private QuestionTag questionTag;
    private ArrayList<Segment> hints;
    private ArrayList<Solution> solutions;
    private ArrayList<Comment> comments;

    public Question(UUID authorID, String title, Difficulty difficulty, ArrayList<Segment> segments, QuestionTag questionTag, ArrayList<Segment> hints, ArrayList<Solution> solutions) {
        this.questionID = UUID.randomUUID();
        this.authorID = authorID;
        this.title = title;
        this.datePosted = new Date().toString();
        this.difficulty = difficulty;
        this.segments = segments;
        this.questionTag = questionTag;
        this.hints = hints;
        this.solutions = solutions;
        this.comments = new ArrayList<>();
        this.recommendedTime = -1;
        this.rating = -1;
    }
    public Question(UUID questionID, UUID authorID, String title, String datePosted, int recommendedTime, Difficulty difficulty, ArrayList<Segment> segments, QuestionTag questionTag, ArrayList<Segment> hints, ArrayList<Solution> solutions) {
        this.questionID = questionID;
        this.authorID = authorID;
        this.title = title;
        this.datePosted = datePosted;
        this.recommendedTime = recommendedTime;
        this.difficulty = difficulty;
        this.segments = segments;
        this.questionTag = questionTag;
        this.hints = hints;
        this.solutions = solutions;
        this.comments = new ArrayList<>();
        this.rating = -1;
    }
    public Question(UUID questionID, UUID authorID, String title, String datePosted, Difficulty difficulty, ArrayList<Segment> segments, QuestionTag questionTag, ArrayList<Segment> hints, ArrayList<Solution> solutions) {
        this.questionID = questionID;
        this.authorID = authorID;
        this.title = title;
        this.datePosted = datePosted;
        this.difficulty = difficulty;
        this.segments = segments;
        this.questionTag = questionTag;
        this.hints = hints;
        this.solutions = solutions;
        this.comments = new ArrayList<>();
        this.rating = -1;
    }

    public UUID getQuestionID() {return questionID;}
    public UUID getAuthorID() {return authorID;}
    public String getTitle() {return title;}
    public String getDatePosted() {return datePosted;}
    public int getRating() {return rating;}
    public int getRecommendedTime() {return recommendedTime;}
    public Difficulty getDifficulty() {return difficulty;}
    public ArrayList<Segment> getSegments() {return segments;}
    public QuestionTag getQuestionTag() {return questionTag;}
    public ArrayList<Segment> getHints() {return hints;}
    public ArrayList<Solution> getSolutions() {return solutions;}
    public ArrayList<Comment> getComments() {return comments;}


    public void setAuthorID(UUID authorID) {

    }
    public void setTitle(String title) {

    }
    public void setRating(int rating) {

    }
    public void setRecommendedTime(int recommendedTime) {

    }
    public void setDifficulty(Difficulty difficulty) {

    }
    public void setSegments(ArrayList<Segment> segments) {

    }
    public void setQuestionTag(QuestionTag questionTag) {

    }
    public void setHints(ArrayList<Segment> hints) {

    }
    public void setSolutions(ArrayList<Solution> solutions) {

    }

    public boolean addRating(int rating) {
        return true;
    }
    public int calculateRating(int rating) {
        return -1;
    }
    public boolean addCommnet(String text, UUID authorID) {
        return true;
    }
    public boolean removeComment(UUID accountID, String datePosted) {
        return true;
    }
    public boolean addHint(Segment segment) {
        return true;
    }
    public boolean addSolution(UUID authorID, String title, Language language, ArrayList<Segment> segments, boolean approved) {
        return true;
    }

    
}
