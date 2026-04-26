package com.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.UUID;

public class CommentTest {

    @Test
    public void constructorWithTextAndAccountID_setsFieldsCorrectly() {
        UUID accountID = UUID.randomUUID();
        Comment comment = new Comment("Test comment", accountID);
        assertEquals("Test comment", comment.getText());
        assertEquals(accountID, comment.getAccountID());
        assertNotNull(comment.getDatePosted());
        assertEquals(0, comment.getLikes());
        assertNotNull(comment.getReplies());
        assertTrue(comment.getReplies().isEmpty());
    }

    @Test
    public void constructorWithAllFields_setsAllFieldsCorrectly() {
        UUID accountID = UUID.randomUUID();
        ArrayList<Comment> replies = new ArrayList<>();
        replies.add(new Comment("Reply", UUID.randomUUID()));
        Comment comment = new Comment("Test comment", accountID, replies, 5, "2023-01-01");
        assertEquals("Test comment", comment.getText());
        assertEquals(accountID, comment.getAccountID());
        assertEquals(replies, comment.getReplies());
        assertEquals(5, comment.getLikes());
        assertEquals("2023-01-01", comment.getDatePosted());
    }

    @Test
    public void setText_updatesText() {
        Comment comment = new Comment("Original", UUID.randomUUID());
        comment.setText("Updated");
        assertEquals("Updated", comment.getText());
    }

    @Test
    public void setAccountID_updatesAccountID() {
        Comment comment = new Comment("Test", UUID.randomUUID());
        UUID newID = UUID.randomUUID();
        comment.setAccountID(newID);
        assertEquals(newID, comment.getAccountID());
    }

    @Test
    public void setDatePosted_updatesDatePosted() {
        Comment comment = new Comment("Test", UUID.randomUUID());
        comment.setDatePosted("2023-12-31");
        assertEquals("2023-12-31", comment.getDatePosted());
    }

    @Test
    public void addReply_validInputs_addsReplyAndReturnsTrue() {
        Comment comment = new Comment("Test", UUID.randomUUID());
        UUID replyID = UUID.randomUUID();
        boolean result = comment.addReply(replyID, "Reply text");
        assertTrue(result);
        assertEquals(1, comment.getReplies().size());
        assertEquals("Reply text", comment.getReplies().get(0).getText());
        assertEquals(replyID, comment.getReplies().get(0).getAccountID());
    }

    @Test
    public void addReply_nullAccountID_returnsFalse() {
        Comment comment = new Comment("Test", UUID.randomUUID());
        boolean result = comment.addReply(null, "Reply text");
        assertFalse(result);
        assertTrue(comment.getReplies().isEmpty());
    }

    @Test
    public void addReply_nullText_returnsFalse() {
        Comment comment = new Comment("Test", UUID.randomUUID());
        boolean result = comment.addReply(UUID.randomUUID(), null);
        assertFalse(result);
        assertTrue(comment.getReplies().isEmpty());
    }

    @Test
    public void addReply_emptyText_returnsFalse() {
        Comment comment = new Comment("Test", UUID.randomUUID());
        boolean result = comment.addReply(UUID.randomUUID(), "");
        assertFalse(result);
        assertTrue(comment.getReplies().isEmpty());
    }

    @Test
    public void getAccountComments_returnsFilteredReplies() {
        Comment comment = new Comment("Test", UUID.randomUUID());
        UUID account1 = UUID.randomUUID();
        UUID account2 = UUID.randomUUID();
        comment.addReply(account1, "Reply 1");
        comment.addReply(account2, "Reply 2");
        comment.addReply(account1, "Reply 3");

        ArrayList<Comment> account1Replies = comment.getAccountComments(account1);
        assertEquals(2, account1Replies.size());
        assertEquals("Reply 1", account1Replies.get(0).getText());
        assertEquals("Reply 3", account1Replies.get(1).getText());

        ArrayList<Comment> account2Replies = comment.getAccountComments(account2);
        assertEquals(1, account2Replies.size());
        assertEquals("Reply 2", account2Replies.get(0).getText());
    }

    @Test
    public void removeComment_validIndex_removesCommentAndReturnsTrue() {
        Comment comment = new Comment("Test", UUID.randomUUID());
        UUID accountID = UUID.randomUUID();
        comment.addReply(accountID, "Reply 1");
        comment.addReply(accountID, "Reply 2");

        boolean result = comment.removeComment(accountID, 0);
        assertTrue(result);
        assertEquals(1, comment.getReplies().size());
        assertEquals("Reply 2", comment.getReplies().get(0).getText());
    }

    @Test
    public void removeComment_invalidIndex_returnsFalse() {
        Comment comment = new Comment("Test", UUID.randomUUID());
        UUID accountID = UUID.randomUUID();
        comment.addReply(accountID, "Reply 1");

        boolean result = comment.removeComment(accountID, 5); // Index out of bounds
        assertFalse(result);
        assertEquals(1, comment.getReplies().size()); // Reply should still be there
    }

    @Test
    public void like_increasesLikeCount() {
        Comment comment = new Comment("Test", UUID.randomUUID());
        assertEquals(0, comment.getLikes());
        comment.like();
        assertEquals(1, comment.getLikes());
        comment.like();
        assertEquals(2, comment.getLikes());
    }
}