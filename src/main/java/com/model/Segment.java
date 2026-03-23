package com.model;

/**
 * Represents a titled content segment used in questions, hints, and solutions.
 * @author Coffee Coders
 */
public class Segment {
    private String title;
    private String desc;
    private DataType dataType;
    private String data;

    /**
     * Creates a segment with title, description, data type, and data content.
     *
     * @param title segment title
     * @param desc segment description
     * @param dataType segment data type
     * @param data segment data content
     */
    public Segment(String title, String desc, DataType dataType, String data) {
        this.title = title;
        this.desc = desc;
        this.dataType = dataType;
        this.data = data;
    }

    /**
     * Returns the segment title.
     *
     * @return segment title
     */
    public String getTitle() {return title;}

    /**
     * Returns the segment description.
     *
     * @return segment description
     */
    public String getDesc() {return desc;}

    /**
     * Returns the segment data type.
     *
     * @return data type
     */
    public DataType getDataType() {return dataType;}

    /**
     * Returns the segment data content.
     *
     * @return data content
     */
    public String getData() {return data;}
    
    /**
     * Sets the segment title when the value is not blank.
     *
     * @param title new title
     */
    public void setTitle(String title) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
    }

    /**
     * Sets the segment description when the value is not blank.
     *
     * @param desc new description
     */
    public void setDesc(String desc) {
        if (desc != null && !desc.isBlank()) {
            this.desc = desc;
        }
    }

    /**
     * Updates the segment data type and content.
     *
     * @param dataType new data type
     * @param data new data content
     * @return true if update succeeds, otherwise false
     */
    public boolean editData(DataType dataType, String data) {
        if (dataType == null || data == null || data.isBlank()) {
            return false;
        }
        this.dataType = dataType;
        this.data = data;
        return true;
    }
}

