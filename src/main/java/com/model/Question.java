package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Represents a coding question with metadata, content, hints, solutions, and comments.
 * @author Coffee Coders
 */
public class Question {
    private UUID questionID;
    private UUID authorID;
    private String title;
    private float rating;
    private int totalRatings;
    private String datePosted;
    private int recommendedTime;
    private Difficulty difficulty;
    private ArrayList<Segment> segments;
    private QuestionTag questionTag;
    private ArrayList<Segment> hints;
    private ArrayList<Solution> solutions;
    private ArrayList<Comment> comments;

    /**
     * Creates a new question with default rating and recommended time values.
     *
     * @param authorID author account ID
     * @param title question title
     * @param difficulty question difficulty
     * @param segments question content segments
     * @param questionTag question tag metadata
     * @param hints list of hint segments
     */
    public Question(UUID authorID, String title, Difficulty difficulty, ArrayList<Segment> segments,
        QuestionTag questionTag, ArrayList<Segment> hints) {
        this.questionID = UUID.randomUUID();
        this.authorID = authorID;
        this.title = title;
        this.datePosted = new Date().toString();
        this.rating = -1;
        this.totalRatings = 0;
        this.recommendedTime = -1;
        this.difficulty = difficulty;
        this.segments = segments;
        this.questionTag = questionTag;
        this.hints = hints;
        this.solutions = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    /**
     * Creates a new question with a provided recommended completion time.
     *
     * @param authorID author account ID
     * @param title question title
     * @param recommendedTime recommended solve time
     * @param difficulty question difficulty
     * @param segments question content segments
     * @param questionTag question tag metadata
     * @param hints list of hint segments
     */
    public Question(UUID authorID, String title, int recommendedTime, Difficulty difficulty, ArrayList<Segment> segments,
        QuestionTag questionTag, ArrayList<Segment> hints) {
        this.questionID = UUID.randomUUID();
        this.authorID = authorID;
        this.title = title;
        this.datePosted = new Date().toString();
        this.recommendedTime = recommendedTime;
        this.difficulty = difficulty;
        this.segments = segments;
        this.questionTag = questionTag;
        this.hints = hints;
        this.solutions = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.rating = -1;
        this.totalRatings = 0;
        
    }

    /**
     * Creates a question from stored data loaded from JSON.
     *
     * @param questionID question ID
     * @param authorID author ID
     * @param title question title
     * @param datePosted posted date
     * @param rating current rating value
     * @param totalRatings total number of ratings
     * @param recommendedTime recommended solve time
     * @param difficulty question difficulty
     * @param segments question segments
     * @param questionTag question tag metadata
     * @param hints hint segments
     * @param solutions submitted solutions
     * @param comments question comments
     */
    public Question(UUID questionID, UUID authorID, String title, String datePosted, float rating, int totalRatings, int recommendedTime, Difficulty difficulty, ArrayList<Segment> segments, QuestionTag questionTag, ArrayList<Segment> hints, ArrayList<Solution> solutions, ArrayList<Comment> comments) {
        this.questionID = questionID;
        this.authorID = authorID;
        this.title = title;
        this.datePosted = datePosted;
        this.difficulty = difficulty;
        this.segments = segments;
        this.questionTag = questionTag;
        this.hints = hints;
        this.solutions = solutions;
        this.comments = comments;
        this.rating = rating;
        this.totalRatings = totalRatings;
        this.recommendedTime = recommendedTime;
    }

    /**
     * Returns the question ID.
     *
     * @return question ID
     */
    public UUID getQuestionID() {return questionID;}

    /**
     * Returns the author account ID.
     *
     * @return author ID
     */
    public UUID getAuthorID() {return authorID;}

    /**
     * Returns the question title.
     *
     * @return question title
     */
    public String getTitle() {return title;}

    /**
     * Returns the date this question was posted.
     *
     * @return posted date string
     */
    public String getDatePosted() {return datePosted;}

    /**
     * Returns the current rating value.
     *
     * @return rating value
     */
    public float getRating() {
        if(rating == -1)
            return 0;
        return rating;
    }

    /**
     * Returns the total number of ratings.
     *
     * @return rating count
     */
    public int getTotalRatings() {return totalRatings;}

    /**
     * Returns the recommended solve time.
     *
     * @return recommended time
     */
    public int getRecommendedTime() {return recommendedTime;}

    /**
     * Returns the difficulty level.
     *
     * @return difficulty
     */
    public Difficulty getDifficulty() {return difficulty;}

    /**
     * Returns the main question content segments.
     *
     * @return segment list
     */
    public ArrayList<Segment> getSegments() {return segments;}

    /**
     * Returns the question tag metadata.
     *
     * @return question tag
     */
    public QuestionTag getQuestionTag() {return questionTag;}

    /**
     * Returns the hint segments.
     *
     * @return hint list
     */
    public ArrayList<Segment> getHints() {return hints;}

    /**
     * Returns the submitted solutions.
     *
     * @return solution list
     */
    public ArrayList<Solution> getSolutions() {return solutions;}

    /**
     * Returns the question comments.
     *
     * @return comment list
     */
    public ArrayList<Comment> getComments() {return comments;}


    /**
     * Sets the author ID.
     *
     * @param authorID new author ID
     */
    public void setAuthorID(UUID authorID) {
        this.authorID = authorID;
    }

    /**
     * Sets the question title.
     *
     * @param title new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the current rating.
     *
     * @param rating new rating value
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Sets the total number of ratings.
     *
     * @param totalRatings new rating count
     */
    public void setTotalRating(int totalRatings) {
        this.totalRatings = totalRatings;
    }

    /**
     * Sets the recommended solve time.
     *
     * @param recommendedTime new recommended time
     */
    public void setRecommendedTime(int recommendedTime) {
        this.recommendedTime = recommendedTime;
    }

    /**
     * Sets the difficulty level.
     *
     * @param difficulty new difficulty
     */
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Replaces the question content segments.
     *
     * @param segments new segment list
     */
    public void setSegments(ArrayList<Segment> segments) {
        this.segments = segments;
    }

    /**
     * Sets the question tag metadata.
     *
     * @param questionTag new question tag
     */
    public void setQuestionTag(QuestionTag questionTag) {
        this.questionTag = questionTag;
    }

    /**
     * Replaces the hint list.
     *
     * @param hints new hint list
     */
    public void setHints(ArrayList<Segment> hints) {
        this.hints = hints;
    }

    /**
     * Replaces the solution list.
     *
     * @param solutions new solution list
     */
    public void setSolutions(ArrayList<Solution> solutions) {
        this.solutions = solutions;
    }

    /**
     * Adds a user rating to this question.
     *
     * @param rating rating value from 1 to 5
     * @return true if rating is accepted, otherwise false
     */
    public boolean addRating(float rating) {
        if( rating < 0 || rating > 5) {
            return false;
        }
        this.rating = calculateRating(rating);
        this.totalRatings++;
        return true;
    }

    /**
     * Calculates the updated rating after a new rating is provided.
     *
     * @param rating new rating value
     * @return updated rating
     */
    public float calculateRating(float rating) {
        if (this.rating == -1) {
            this.totalRatings = 0;
            return rating;
        }
        return (this.rating*this.totalRatings + rating) / (this.totalRatings+1);
    }

    /**
     * Adds a comment to this question.
     *
     * @param text comment text
     * @param authorID author account ID
     * @return true if comment is added, otherwise false
     */
    public boolean addComment(String text, UUID authorID) {
        if (text == null || text.isEmpty() || authorID == null) {
            return false;
        }
        Comment comment = new Comment(text, authorID);
        comments.add(comment);
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
        String date = accountReplies.get(index).getDatePosted();
        for(Comment comment: comments) {
            if(comment.getDatePosted().equals(date)) {
                comments.remove(comment);
                return true;
            }
        }
        return false;
    }

     /**
     * Returns the replies for a user.
     *
     * @param accountID A account's ID
     * @return list of replies
     */
    public ArrayList<Comment> getAccountComments(UUID accountID) {
        ArrayList<Comment> filtered = new ArrayList<>();
        for(Comment comment: comments){
            if(comment.getAccountID() == accountID)
                filtered.add(comment);
        }
        return filtered;
    }

    /**
     * Adds a hint segment to this question.
     *
     * @param segment hint segment to add
     * @return true if hint is added, otherwise false
     */
    public boolean addHint(Segment segment) {
        if (segment == null) {
            return false;
        }
        hints.add(segment);
        return true;
    }

    /**
     * Adds a new solution to this question.
     *
     * @param authorID solution author ID
     * @param title solution title
     * @param language solution language
     * @param segments solution segments
     * @return true if solution is added, otherwise false
     */
    public boolean addSolution(UUID authorID, String title, Language language, ArrayList<Segment> segments) {
        if( authorID == null ||title == null || title.isEmpty() || language == null || segments == null || segments.isEmpty()) {
            return false;
        } 
        Solution solution = new Solution(authorID, title, language, segments);
        solutions.add(solution);
        return true;
    }

    /**
     * Updates the question title if valid.
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
     * Replaces question segments if valid.
     *
     * @param segments new segment list
     * @return true if question content is updated, otherwise false
     */
    public boolean editQuestion(ArrayList<Segment> segments) {
        if (segments == null || segments.isEmpty()) {
            return false;
        }
        this.segments = segments;
        return true;
    }

    /**
     * Formats a date into mm/dd/yyyy 
     * 
     * @return a date formatted in mm/dd/yyyy
     */
    public String formatDate(String date) {
        String formattedDate = date;
        String month = formattedDate.substring(0,2);
        String day = formattedDate.substring(2,4);
        String year = formattedDate.substring(4,8);
        formattedDate = month + "/" + day + "/" + year;
        return formattedDate;
    }

    
}
