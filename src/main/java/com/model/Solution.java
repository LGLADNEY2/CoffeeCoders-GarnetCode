package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class Solution {
    private UUID authorID;
    private String title;
    private Language language;
    private ArrayList<Segment> segments;
    private ArrayList<Comment> comments;
    private int likes;

    public Solution(UUID authorID, String title, Language language, ArrayList<Segment> segments) {
        this.authorID = authorID;
        this.title = title;
        this.language = language;
        this.segments = segments;
        this.likes = 0;
        this.comments = new ArrayList<>();
    }

    public Solution(UUID authorID, String title, Language language, ArrayList<Segment> segments, ArrayList<Comment> comments, int likes) {
        this.authorID = authorID;
        this.title = title;
        this.language = language;
        this.segments = segments;
        this.likes = likes;
        this.comments = comments;
    }

    public UUID getAuthorID() {return authorID;}
    public String getTitle() {return title;}
    public Language getLanguage() {return language;}
    public ArrayList<Segment> getSegments() {return segments;}
    public ArrayList<Comment> getComments() {return comments;}
    public int getLikes() {return likes;}

    public void setAuthorID(UUID authorID) {

    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setLanguage(Language language) {

    }
    public void setSegments(ArrayList<Segment> segments) {
        this.segments = segments;
    }

    public void like() {
        this.likes++;
    }
    public boolean addComment(String text, UUID authorID) {
        if (text == null || text.isEmpty() || authorID == null) {
        return false;
    }
        comments.add(new Comment(text, authorID));
        return true;
    }
    public boolean removeComment(UUID accountID, String datePosted) {
        return true;
    }

    public boolean editTitle(String title) {
        if (title == null || title.isEmpty()) {
            return false;
        }
        this.title = title;
        return true;
    }

    public boolean editSolution(ArrayList<Segment> segments) {
        if (segments == null || segments.isEmpty()) {
            return false;
        }
        this.segments = segments;
        return true;
    }

    
}
