package com.model;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.UUID;
import java.util.ArrayList;

public class EditorTest {
    @Test
    public void constructorWithUsernamePassword_setsAdminTrue() {
        Editor editor = new Editor("editorUser", "editorPass");
        assertTrue(editor.getAdmin());
        assertEquals("editorUser", editor.getUsername());
        assertEquals("editorPass", editor.getPassword());
    }

    @Test
    public void constructorWithAllFields_setsAdminFlag() {
        UUID id = UUID.randomUUID();
        Editor editor = new Editor(id, "First", "Last", "email@test.com", "user", "pass", Role.EDITOR, 1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), true);
        assertTrue(editor.getAdmin());
        Editor editor2 = new Editor(id, "First", "Last", "email@test.com", "user", "pass", Role.EDITOR, 1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false);
        assertFalse(editor2.getAdmin());
    }

    @Test
    public void setAdmin_setsAdminFlag() {
        Editor editor = new Editor("user", "pass");
        editor.setAdmin(false);
        assertFalse(editor.getAdmin());
        editor.setAdmin(true);
        assertTrue(editor.getAdmin());
    }

    @Test
    public void createQuestion_returnsTrue() {
        Editor editor = new Editor("user", "pass");
        assertTrue(editor.createQuestion(null));
    }

    @Test
    public void editQuestion_returnsTrue() {
        Editor editor = new Editor("user", "pass");
        assertTrue(editor.editQuestion(null));
    }

    @Test
    public void createSolution_returnsTrue() {
        Editor editor = new Editor("user", "pass");
        assertTrue(editor.createSolution(null, null));
    }

    @Test
    public void approveQuestion_returnsTrue() {
        Editor editor = new Editor("user", "pass");
        assertTrue(editor.approveQuestion(true, null));
    }

    @Test
    public void approveSolution_returnsTrue() {
        Editor editor = new Editor("user", "pass");
        assertTrue(editor.approveSolution(true, null));
    }

    @Test
    public void removeQuestion_returnsTrue() {
        Editor editor = new Editor("user", "pass");
        assertTrue(editor.removeQuestion(true, null));
    }

    @Test
    public void removeSolution_returnsTrue() {
        Editor editor = new Editor("user", "pass");
        assertTrue(editor.removeSolution(true, null));
    }

    @Test
    public void addTrustedRole_returnsTrue() {
        Editor editor = new Editor("user", "pass");
        assertTrue(editor.addTrustedRole(true, UUID.randomUUID(), null));
    }

    @Test
    public void addAdmin_returnsTrue() {
        Editor editor = new Editor("user", "pass");
        assertTrue(editor.addAdmin(UUID.randomUUID()));
    }

    @Test
    public void removeAdmin_returnsTrue() {
        Editor editor = new Editor("user", "pass");
        assertTrue(editor.removeAdmin(UUID.randomUUID()));
    }
}
