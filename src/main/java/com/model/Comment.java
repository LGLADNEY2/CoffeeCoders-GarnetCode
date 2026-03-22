package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Comment {
    private String text;
    private UUID accountID;
    private ArrayList<Comment> replies;
    private int likes;
    private String datePosted;

    public Comment(String text, UUID accountID, String datePosted) {
        this.text = text;
        this.accountID = accountID;
        this.datePosted = new Date().toString();
        this.replies = new ArrayList<>();
        this.likes = 0;
    }

    public Comment(String text, UUID accountID, ArrayList<Comment> replies, int likes, String datePosted) {
        this.text = text;
        this.accountID = accountID;
        this.datePosted = datePosted;
        this.replies = replies;
        this.likes = likes;
    }

    public String getText() {return text;}
    public UUID getAccountID() {return accountID;}
    public String getDatePosted() {return datePosted;}
    public ArrayList<Comment> getReplies() {return replies;}
    public int getLikes() {return likes;}

    public void setText() {

    }
    public void setAccountID() {

    }
    public void setDatePosted() {

    }

    public boolean addReply(UUID accountID, String text, String datePosted) {
        if (accountID == null || text == null || text.isEmpty()) {
            return false;
        }
        Comment reply = new Comment(text, accountID, datePosted);
        replies.add(reply);
        return true;
    }
    public boolean removeReply(UUID accountID, String datePosted) {
        return true;
    }
    public void like() {
        this.likes++;
    }

    
}
