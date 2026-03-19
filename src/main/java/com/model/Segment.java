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
            if (dataType == null || data == null || data.isBlank()) {
                return false;
            }
            this.dataType = dataType;
            this.data = data;
        return true;
    }
    
    public void setTitle(String title) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
    }

    public void setDesc(String desc) {
        if (desc != null && !desc.isBlank()) {
            this.desc = desc;
        }
    }
}

