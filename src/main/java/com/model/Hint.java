package com.model;

public class Hint {
    private String text;
    private String code;

    public Hint(String text, String code) {
        this.text = text;
        this.code = code;
    }

    public Hint(String text) {
        this.text = text;
    }

    public boolean editHint(String text, String code) {
        return true;
    }
}
