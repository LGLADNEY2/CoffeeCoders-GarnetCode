// Copyright 2026 Christopher Tytone

import java.util.ArrayList;
import java.util.UUID;

public class Question {
    private UUID id;
    private String title;
    private Difficulty difficulty;
    private QuestionTag tag;
    private String desc;
    private ArrayList<Hint> hints;
    private int recMin;
    private ArrayList<Solution> solutions;

    public Question(UUID id, String title, Difficulty difficulty, QuestionTag tag, String desc,
                    ArrayList<Hint> hints, int recMin, ArrayList<Solution> solutions) {
        this.id = id;
        this.title = title;
        this.difficulty = difficulty;
        this.tag = tag;
        this.desc = desc;
        this.hints = hints;
        this.recMin = recMin;
        this.solutions = solutions;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public QuestionTag getTag() {
        return tag;
    }

    public String getDesc() {
        return desc;
    }

    public ArrayList<Hint> getHints() {
        return hints;
    }

    public int getRecMin() {
        return recMin;
    }

    public ArrayList<Solution> getSolutions() {
        return solutions;
    }

    public boolean isCompletedBy(String user) {
        return false;
    }
}