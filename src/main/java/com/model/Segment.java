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

    public String getTitle() {return title;}
    public String getDesc() {return desc;}
    public String getDataType() {return dataType;}
    public String getData() {return data;}

    public boolean editData(String dataType, String data) {
        return true;
    }
    
    public void setTitle(String title) {

    }

    public void setDesc(String desc) {

    }
    
}
