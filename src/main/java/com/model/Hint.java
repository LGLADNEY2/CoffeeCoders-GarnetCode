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
        this.code = "";
    }

    public String getText() {return text;}
    public String getCode() {return code;}

    public void setText(String text) {

    }

    public void setCode(String code) {
        
    }
}
