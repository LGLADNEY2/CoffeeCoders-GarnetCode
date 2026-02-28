package com.model;

public class Segment {
    private String title;
    private String desc;
    private String dataType;
    private String data;

    public Segment(String title, String desc, String dataType, String data) {
        this.title = title;
        this.desc = desc;
        this.dataType = dataType;
        this.data = data;
    }

    public boolean editData(String dataType, String data) {
        return true;
    }
    
    public boolean editTitle(String title) {
        return true;
    }

    public boolean editDesc(String desc) {
        return true;
    }
    
}
