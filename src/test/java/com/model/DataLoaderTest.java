package com.model;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.UUID;

public class DataLoaderTest {
    @Test
    public void getAccounts_returnsListEvenIfFileMissingOrEmpty() {
        ArrayList<Account> accounts = DataLoader.getAccounts();
        assertNotNull(accounts);
    }

    @Test
    public void getQuestions_returnsListEvenIfFileMissingOrEmpty() {
        ArrayList<Question> questions = DataLoader.getQuestions();
        assertNotNull(questions);
    }

    @Test
    public void toInt_withNumber_returnsIntValue() throws Exception {
        // Use reflection to access private method
        java.lang.reflect.Method method = DataLoader.class.getDeclaredMethod("toInt", Object.class);
        method.setAccessible(true);
        assertEquals(5, method.invoke(null, 5));
        assertEquals(42, method.invoke(null, "42"));
        assertEquals(0, method.invoke(null, ""));
        assertEquals(0, method.invoke(null, null));
    }

    @Test
    public void parseComments_withNull_returnsEmptyList() throws Exception {
        java.lang.reflect.Method method = DataLoader.class.getDeclaredMethod("parseComments", org.json.simple.JSONArray.class);
        method.setAccessible(true);
        Object result = method.invoke(null, (Object) null);
        assertTrue(result instanceof ArrayList);
        assertTrue(((ArrayList<?>) result).isEmpty());
    }
}
