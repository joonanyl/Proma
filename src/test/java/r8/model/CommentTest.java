package r8.model;

import org.junit.jupiter.api.*;
import r8.model.task.Task;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentTest {
// kohghjl
    private static Task task, task2;
    private static Comment comment, reply, reply2;
    private static Account account, account2;
    private static String content, replyContent, replyContent2;

    @BeforeAll
    static void setUpBeforeTesting() {
        task = new Task();
        task2 = new Task();
        task.setTaskId(11);
        task2.setTaskId(12);
        content = "comment content :) ";
        replyContent = "this is a reply to a comment!";
        replyContent2 = "this is a second reply to a comment!";

        reply = new Comment(account, replyContent);
        reply2 = new Comment(account2, replyContent2);

        account = new Account("fname", "lname", "mail", "pwd");
        account2 = new Account("fname2", "lname2", "mail2", "pwd2");
        comment = new Comment(account, content);
        comment.setCommentId(1);
    }

    @Test
    @Order(1)
    void setAndGetAccount() {
        comment.setAccount(account);
        assertEquals(account, comment.getAccount(), "Kirjoittajan (account) asettaminen kommentille epäonnistui");
        comment.setAccount(account2);
        assertEquals(account2, comment.getAccount(), "Kirjoittajan (account) vaihtaminen epäonnistui");
    }

    @Test
    @Order(2)
    void setAndGetTaskID() {
        comment.setTaskID(12);
        assertEquals(task2.getTaskId(), comment.getTaskID(), "TehtäväID ei täsmää");

        comment.setTaskID(11);
        assertEquals(task.getTaskId(), comment.getTaskID(), "TehtäväID ei täsmää (2)");
    }

    @Test
    @Order(3)
    void getAndSetContent() {
        assertEquals(content, comment.getContent(), "Kommenttiteksti ei täsmää");

        String newContent = content + ".. pieni lisäys perään";
        comment.setContent(newContent);

        assertEquals(newContent, comment.getContent(), "Kommenttitekstin muokkaaminen epäonnistui");
    }

    @Test
    @Order(4)
    void addReply() {
        comment.addReply(reply);
        comment.addReply(reply2);
        //assertEquals(comment.getCommentId(), reply.getParentCommentID(), "Parent-kommentin asettaminen vastaukselle epäonnistui");
        //assertEquals(comment.getCommentId(), reply2.getParentCommentID(), "Parent-kommentin asettaminen vastaukselle epäonnistui");

        assertTrue(comment.getChildComments().contains(reply), "Vastuasta ei löydy kommentin vastauslistalta");
        assertTrue(comment.getChildComments().contains(reply2), "Vastuasta ei löydy kommentin vastauslistalta (2)");
    }

    @Test
    @Order(5)
    void setChildComments() {
        Set<Comment> replySet = new HashSet<>();

        Comment reply3 = new Comment(account, "yet another reply");
        Comment reply4 = new Comment(account, "yet another reply 2");

        replySet.add(reply3);
        replySet.add(reply4);

        comment.setChildComments(replySet);
        assertTrue(comment.getChildComments().containsAll(replySet), "Monen vastauksen lisääminen kommentille kerrallaan epäonnistui");
    }
}