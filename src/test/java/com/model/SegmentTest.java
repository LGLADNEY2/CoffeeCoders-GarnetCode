package com.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SegmentTest {

    @Test
    public void constructor_setsFieldsCorrectly() {
        Segment segment = new Segment("Test Title", "Test Description", DataType.STRING, "Test Data");

        assertEquals("Test Title", segment.getTitle());
        assertEquals("Test Description", segment.getDesc());
        assertEquals(DataType.STRING, segment.getDataType());
        assertEquals("Test Data", segment.getData());
    }

    @Test
    public void getTitle_returnsTitle() {
        Segment segment = new Segment("Title", "Desc", DataType.STRING, "Data");
        assertEquals("Title", segment.getTitle());
    }

    @Test
    public void getDesc_returnsDescription() {
        Segment segment = new Segment("Title", "Description", DataType.STRING, "Data");
        assertEquals("Description", segment.getDesc());
    }

    @Test
    public void getDataType_returnsDataType() {
        Segment segment = new Segment("Title", "Desc", DataType.IMAGE, "Data");
        assertEquals(DataType.IMAGE, segment.getDataType());
    }

    @Test
    public void getData_returnsData() {
        Segment segment = new Segment("Title", "Desc", DataType.STRING, "Content");
        assertEquals("Content", segment.getData());
    }

    @Test
    public void setTitle_validTitle_updatesTitle() {
        Segment segment = new Segment("Old", "Desc", DataType.STRING, "Data");
        segment.setTitle("New Title");
        assertEquals("New Title", segment.getTitle());
    }

    @Test
    public void setTitle_nullTitle_doesNotUpdate() {
        Segment segment = new Segment("Old", "Desc", DataType.STRING, "Data");
        segment.setTitle(null);
        assertEquals("Old", segment.getTitle());
    }

    @Test
    public void setTitle_blankTitle_doesNotUpdate() {
        Segment segment = new Segment("Old", "Desc", DataType.STRING, "Data");
        segment.setTitle("   ");
        assertEquals("Old", segment.getTitle());
    }

    @Test
    public void setTitle_emptyTitle_doesNotUpdate() {
        Segment segment = new Segment("Old", "Desc", DataType.STRING, "Data");
        segment.setTitle("");
        assertEquals("Old", segment.getTitle());
    }

    @Test
    public void setDesc_validDescription_updatesDescription() {
        Segment segment = new Segment("Title", "Old", DataType.STRING, "Data");
        segment.setDesc("New Description");
        assertEquals("New Description", segment.getDesc());
    }

    @Test
    public void setDesc_nullDescription_doesNotUpdate() {
        Segment segment = new Segment("Title", "Old", DataType.STRING, "Data");
        segment.setDesc(null);
        assertEquals("Old", segment.getDesc());
    }

    @Test
    public void setDesc_blankDescription_doesNotUpdate() {
        Segment segment = new Segment("Title", "Old", DataType.STRING, "Data");
        segment.setDesc("   ");
        assertEquals("Old", segment.getDesc());
    }

    @Test
    public void editData_validInputs_updatesDataAndReturnsTrue() {
        Segment segment = new Segment("Title", "Desc", DataType.STRING, "Old Data");
        boolean result = segment.editData(DataType.CODE, "New Code");
        assertTrue(result);
        assertEquals(DataType.CODE, segment.getDataType());
        assertEquals("New Code", segment.getData());
    }

    @Test
    public void editData_nullDataType_returnsFalse() {
        Segment segment = new Segment("Title", "Desc", DataType.STRING, "Data");
        boolean result = segment.editData(null, "New Data");
        assertFalse(result);
        assertEquals(DataType.STRING, segment.getDataType());
        assertEquals("Data", segment.getData());
    }

    @Test
    public void editData_nullData_returnsFalse() {
        Segment segment = new Segment("Title", "Desc", DataType.STRING, "Data");
        boolean result = segment.editData(DataType.CODE, null);
        assertFalse(result);
        assertEquals(DataType.STRING, segment.getDataType());
        assertEquals("Data", segment.getData());
    }

    @Test
    public void editData_blankData_returnsFalse() {
        Segment segment = new Segment("Title", "Desc", DataType.STRING, "Data");
        boolean result = segment.editData(DataType.CODE, "   ");
        assertFalse(result);
        assertEquals(DataType.STRING, segment.getDataType());
        assertEquals("Data", segment.getData());
    }

    @Test
    public void editData_emptyData_returnsFalse() {
        Segment segment = new Segment("Title", "Desc", DataType.STRING, "Data");
        boolean result = segment.editData(DataType.CODE, "");
        assertFalse(result);
        assertEquals(DataType.STRING, segment.getDataType());
        assertEquals("Data", segment.getData());
    }
}