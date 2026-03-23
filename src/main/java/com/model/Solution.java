package com.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents a submitted solution with content segments, comments, and likes.
 * @author Coffee Coders
 */
public class Solution {
    private UUID authorID;
    private String title;
    private Language language;
    private ArrayList<Segment> segments;
    private ArrayList<Comment> comments;
    private int likes;

    /**
     * Creates a solution with default likes and an empty comment list.
     *
     * @param authorID solution author ID
     * @param title solution title
     * @param language solution language
     * @param segments solution content segments
     */
    public Solution(UUID authorID, String title, Language language, ArrayList<Segment> segments) {
        this.authorID = authorID;
        this.title = title;
        this.language = language;
        this.segments = segments;
        this.likes = 0;
        this.comments = new ArrayList<>();
    }

    /**
     * Creates a solution with all fields provided.
     *
     * @param authorID solution author ID
     * @param title solution title
     * @param language solution language
     * @param segments solution content segments
     * @param comments solution comments
     * @param likes current like count
     */
    public Solution(UUID authorID, String title, Language language, ArrayList<Segment> segments, ArrayList<Comment> comments, int likes) {
        this.authorID = authorID;
        this.title = title;
        this.language = language;
        this.segments = segments;
        this.likes = likes;
        this.comments = comments;
    }

    /**
     * Returns the solution author ID.
     *
     * @return author ID
     */
    public UUID getAuthorID() {return authorID;}

    /**
     * Returns the solution title.
     *
     * @return solution title
     */
    public String getTitle() {return title;}

    /**
     * Returns the solution language.
     *
     * @return language
     */
    public Language getLanguage() {return language;}

    /**
     * Returns the solution content segments.
     *
     * @return segment list
     */
    public ArrayList<Segment> getSegments() {return segments;}

    /**
     * Returns the solution comments.
     *
     * @return comment list
     */
    public ArrayList<Comment> getComments() {return comments;}

    /**
     * Returns the number of likes.
     *
     * @return like count
     */
    public int getLikes() {return likes;}

    /**
     * Sets the solution author ID.
     *
     * @param authorID new author ID
     */
    public void setAuthorID(UUID authorID) {
        this.authorID = authorID;
    }

    /**
     * Sets the solution title.
     *
     * @param title new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the solution language.
     *
     * @param language new language
     */
    public void setLanguage(Language language) {
        this.language = language;
    }

    /**
     * Sets the solution segments.
     *
     * @param segments new segment list
     */
    public void setSegments(ArrayList<Segment> segments) {
        this.segments = segments;
    }

    /**
     * Increases the solution's like count by one.
     */
    public void like() {
        this.likes++;
    }

    /**
     * Adds a comment to this solution.
     *
     * @param text comment text
     * @param authorID comment author ID
     * @return true if comment is added, otherwise false
     */
    public boolean addComment(String text, UUID authorID) {
        if (text == null || text.isEmpty() || authorID == null) {
            return false;
        }
        comments.add(new Comment(text, authorID));
        return true;
    }

    /**
     * Removes a comment from this solution.
     *
     * @param accountID comment author ID
     * @param datePosted comment posted date
     * @return result of the remove attempt
     */
    public boolean removeComment(UUID accountID, String datePosted) {
        return true;
    }

    /**
     * Updates the solution title when valid.
     *
     * @param title new title
     * @return true if title is updated, otherwise false
     */
    public boolean editTitle(String title) {
        if (title == null || title.isEmpty()) {
            return false;
        }
        this.title = title;
        return true;
    }

    /**
     * Replaces solution segments when valid.
     *
     * @param segments new segment list
     * @return true if solution is updated, otherwise false
     */
    public boolean editSolution(ArrayList<Segment> segments) {
        if (segments == null || segments.isEmpty()) {
            return false;
        }
        this.segments = segments;
        return true;
    }
}
