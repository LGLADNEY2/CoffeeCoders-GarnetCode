package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Represents a user comment with reply, like, and posting metadata.
 * @author Coffee Coders
 */
public class Comment {
    private String text;
    private UUID accountID;
    private ArrayList<Comment> replies;
    private int likes;
    private String datePosted;

    /**
     * Creates a new comment with default values for date, replies, and likes.
     *
     * @param text comment text
     * @param accountID ID of the account that posted the comment
     */
    public Comment(String text, UUID accountID) {
        this.text = text;
        this.accountID = accountID;
        this.datePosted = new Date().toString();
        this.replies = new ArrayList<>();
        this.likes = 0;
    }

    /**
     * Creates a comment with all fields provided.
     *
     * @param text comment text
     * @param accountID ID of the account that posted the comment
     * @param replies list of replies for this comment
     * @param likes current number of likes
     * @param datePosted date the comment was posted
     */
    public Comment(String text, UUID accountID, ArrayList<Comment> replies, int likes, String datePosted) {
        this.text = text;
        this.accountID = accountID;
        this.datePosted = datePosted;
        this.replies = replies;
        this.likes = likes;
    }

    /**
     * Returns the comment text.
     *
     * @return comment text
     */
    public String getText() {return text;}

    /**
     * Returns the account ID of the user who posted the comment.
     *
     * @return account ID
     */
    public UUID getAccountID() {return accountID;}

    /**
     * Returns the date the comment was posted.
     *
     * @return posted date string
     */
    public String getDatePosted() {return datePosted;}

    /**
     * Returns the replies for this comment.
     *
     * @return list of replies
     */
    public ArrayList<Comment> getReplies() {return replies;}

    /**
     * Returns the number of likes on this comment.
     *
     * @return like count
     */
    public int getLikes() {return likes;}

    /**
     * Returns the replies for a user.
     *
     * @param accountID A account's ID
     * @return list of replies
     */
    public ArrayList<Comment> getAccountComments(UUID accountID) {
        ArrayList<Comment> filtered = new ArrayList<>();
        for(Comment comment: replies){
            if(comment.getAccountID() == accountID)
                filtered.add(comment);
        }
        return filtered;
    }

    /**
     * Updates the text for this comment.
     *
     * @param text new comment text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Updates the account ID for this comment.
     *
     * @param accountID new account ID
     */
    public void setAccountID(UUID accountID) {
        this.accountID = accountID;
    }

    /**
     * Updates the posted date for this comment.
     *
     * @param datePosted new posted date string
     */
    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    /**
     * Adds a reply to this comment.
     *
     * @param accountID account ID of the reply author
     * @param text reply text
     * @param datePosted date the reply was posted
     * @return true if the reply is added, otherwise false
     */
    public boolean addReply(UUID accountID, String text) {
        if (accountID == null || text == null || text.isEmpty()) {
            return false;
        }
        Comment reply = new Comment(text, accountID);
        replies.add(reply);
        return true;
    }

    /**
     * Removes a reply that matches the given account ID and index.
     *
     * @param accountID account ID of the reply author
     * @param index index of the accounts replies
     * @return result of the remove attempt
     */
    public boolean removeComment(UUID accountID, int index) {
        ArrayList<Comment> accountReplies = getAccountComments(accountID);
        if (index < 0 || index >= accountReplies.size()) {
            return false;
        }
        String date = accountReplies.get(index).getDatePosted();
        for(Comment comment: replies) {
            if(comment.getDatePosted().equals(date)) {
                replies.remove(comment);
                return true;
            }
        }
        return false;
    }

    /**
     * Increases the like count by one.
     */
    public void like() {
        this.likes++;
    }

    
}
