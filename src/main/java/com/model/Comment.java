package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class Comment {
    private String text;
    private UUID accountID;
    private String datePosted;
    private ArrayList<Comment> replies;
    private int likes;

    public Comment(String text, UUID accountID, String datePosted) {
        this.text = text;
        this.accountID = accountID;
        this.datePosted = datePosted;
        this.replies = new ArrayList<>();
        this.likes = 0;
    }

    public boolean reply(UUID accountID, String text, String datePosted) {
        return true;
    }

    public void like() {
        
    }

    
}
