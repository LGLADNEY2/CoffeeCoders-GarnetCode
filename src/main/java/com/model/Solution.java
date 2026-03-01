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
    private boolean approved;

    public Solution(UUID authorID, String title, Language language, ArrayList<Segment> segments, boolean approved) {
        this.authorID = authorID;
        this.title = title;
        this.language = language;
        this.segments = segments;
        this.approved = approved;
        this.likes = 0;
        this.comments = new ArrayList<>();
    }
    public Solution(String title, Language language, ArrayList<Segment> segments, boolean approved) {
        this.title = title;
        this.language = language;
        this.segments = segments;
        this.approved = approved;
        this.likes = 0;
        this.comments = new ArrayList<>();
    }

    public UUID getAuthorID() {return authorID;}
    public String getTitle() {return title;}
    public Language getLanguage() {return language;}
    public ArrayList<Segment> getSegments() {return segments;}
    public ArrayList<Comment> getComments() {return comments;}
    public int getLikes() {return likes;}
    public boolean getApproved() {return approved;}

    public void setAuthorID(UUID authorID) {

    }
    public void setTitle(String title) {

    }
    public void setLanguage(Language language) {

    }
    public void setSegments(ArrayList<Segment> segments) {

    }
    public void setApproved(boolean approved) {

    }

    public boolean submit() {
        return true;
    }

    public void like() {

    }
    public boolean addCommnet(String text, UUID authorID) {
        return true;
    }
    public boolean removeComment(UUID accountID, String datePosted) {
        return true;
    }

    
}
