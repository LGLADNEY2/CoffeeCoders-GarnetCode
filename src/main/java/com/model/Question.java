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

    public Question(UUID authorID, String title, int rating,Difficulty difficulty, ArrayList<Segment> segments, QuestionTag questionTag, ArrayList<Segment> hints, ArrayList<Solution> solutions) {
        this.questionID = UUID.randomUUID();
        this.authorID = authorID;
        this.title = title;
        this.rating = rating;
        this.datePosted = new Date().toString();
        this.difficulty = difficulty;
        this.segments = segments;
        this.questionTag = questionTag;
        this.hints = hints;
        this.solutions = solutions;
        this.comments = new ArrayList<>();
        this.recommendedTime = -1;
        ;
    }
    public Question(UUID questionID, UUID authorID, String title, int rating, String datePosted,
        int recommendedTime, Difficulty difficulty, ArrayList<Segment> segments,
        QuestionTag questionTag, ArrayList<Segment> hints, ArrayList<Solution> solutions,
        ArrayList<Comment> comments) {
        this.questionID = questionID;
        this.authorID = authorID;
        this.title = title;
        this.rating = rating;
        this.datePosted = datePosted;
        this.recommendedTime = recommendedTime;
        this.difficulty = difficulty;
        this.segments = segments;
        this.questionTag = questionTag;
        this.hints = hints;
        this.solutions = solutions;
        this.comments = comments;
        
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
        this.authorID = authorID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setRecommendedTime(int recommendedTime) {
        this.recommendedTime = recommendedTime;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setSegments(ArrayList<Segment> segments) {
        this.segments = segments;
    }

    public void setQuestionTag(QuestionTag questionTag) {
        this.questionTag = questionTag;
    }

    public void setHints(ArrayList<Segment> hints) {
        this.hints = hints;
    }

    public void setSolutions(ArrayList<Solution> solutions) {
        this.solutions = solutions;
    }

    public boolean addRating(int rating) {
        if( rating < 1 || rating > 5) {
            return false;
        }
        this.rating = calculateRating(rating);
        return true;
    }

    public int calculateRating(int rating) {
        if (this.rating == -1) {
            return rating;
        }
        return (this.rating + rating) / 2;
    }

    public boolean addComment(String text, UUID authorID) {
        if (text == null || text.isEmpty() || authorID == null) {
            return false;
        }
        Comment comment = new Comment(text, authorID, new Date().toString());
        comments.add(comment);
        return true;
    }

    public boolean removeComment(UUID accountID, String datePosted) {
        for (int i = 0; i < comments.size(); i++) {
            Comment comment = comments.get(i);
            if (comment.getAccountID().equals(accountID) && comment.getDatePosted().equals(datePosted)) {
                comments.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean addHint(Segment segment) {
        if (segment == null) {
            return false;
        }
        hints.add(segment);
        return true;
    }

    public boolean addSolution(UUID authorID, String title, Language language, ArrayList<Segment> segments, boolean approved) {
        if( authorID == null ||title == null || title.isEmpty() || language == null || segments == null || segments.isEmpty()) {
            return false;
        } 
        Solution solution = new Solution(authorID, title, language, segments, approved);
        solutions.add(solution);
        return true;
    }

    public boolean editTitle(String title) {
        if (title == null || title.isEmpty()) {
            return false;
        }
        this.title = title;
        return true;
    }

    public boolean editQuestion(ArrayList<Segment> segments) {
        if (segments == null || segments.isEmpty()) {
            return false;
        }
        this.segments = segments;
        return true;
    }

    
}
