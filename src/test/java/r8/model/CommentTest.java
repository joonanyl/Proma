package r8.model;

import org.junit.jupiter.api.*;
import r8.model.task.Task;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author sanku
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentTest {
    /**
     * A task that is required for initalizing a comment
     */
    static Task t;
    /**
     * Accounts that are required for initalizing a comment
     */
    static Account a, a2;
    /**
     * Strings that will be used troughout testing
     */
    static String c, c2;
    /**
     * Comments and replies that will be used throughout testing
     */
    static Comment parent, reply, reply2, reply3;

    /**
     * BeforeAll-method where all variables required for testing are being initialized
     */
    @BeforeAll
    static void setUpBeforeTesting() {
        t = new Task();
        a = new Account();
        a2 = new Account();
        a.setFirstName("account1");
        a2.setFirstName("account2");
        c = "content";
        c2 = "content2";
        parent = new Comment(t, a, c);
    }

    /**
     * Test case where a parent comment's constructor is being tested
     */
    @Test
    @Order(1)
    void mainContructor() {
        assertEquals(t, parent.getTask(), "Task ei täsmää");
        assertEquals(a.getFirstName(), parent.getAccount().getFirstName(), "Account ei täsmää");
        assertEquals(c, parent.getContent(), "Teksti ei täsmää");
    }
    /**
     * Test case where a reply comment's constructor is being tested
     */
    @Test
    @Order(2)
    void replyConstructor(){
        reply = new Comment(parent, a2, c2);
        assertEquals(a2.getFirstName(), reply.getAccount().getFirstName(), "Account ei täsmää");
        assertEquals(c2, reply.getContent(), "Teksti ei täsmää");
        assertEquals(parent, reply.getParentComment(), "Alkuperäinen kommentti ei täsmää");
    }

    /**
     * Test case where editing a comments content is being tested
     */
    @Test
    @Order(3)
    void editText(){
        String newText = parent.getContent() + " vol2";
        parent.editText(newText);

        assertEquals(newText, parent.getContent(), "Uusi teksti ei päivittynyt");
        assertNotEquals(c, parent.getContent(), "Uusi teksti ei päivittynyt (2)");
    }

    /**
     * Test case where adding a reply to a comment is being tested
     */
    @Test
    @Order(4)
    void addReply(){
        parent.addReply(reply);
        assertTrue(parent.getChildComments().contains(reply), "Vastausta ei löytynyt");
        assertEquals(parent, reply.getParentComment(), "Alkuperäinen kommentti ei täsmää");
    }

    /**
     * Test cases where a parent comment's child comments ( set on reply comments) is being tested in various of ways
     */
    @Test
    @Order(5)
    void testingChildcomments(){
        reply2 = new Comment(parent, a, "this is a second reply");
        reply3 = new Comment(parent, a2, "this is a third reply");
        Set<Comment> replies = new HashSet<>();
        replies.add(reply2);
        replies.add(reply3);
        parent.setChildComments(replies);
        Set<Comment> children = parent.getChildComments();
        assertTrue(children.contains(reply2), "toista vastausta ei löytynyt kommentin vastauksista");
        assertTrue(children.contains(reply3), "kolmatta vastausta ei löytynyt kommentin vastauksista");
    }

    /**
     * Test case where changing a reply comment's parent comment is being tested
     */
    @Test
    @Order(6)
    void changeParent(){
        Comment newP = new Comment(t, a, "new parent commment");
        reply.setParentComment(newP);
        newP.addReply(reply);

        assertFalse(parent.getChildComments().contains(reply), "vastaus ei poistunut alkuperäisestä kommentista, vaikka parent vaihtui");
        assertEquals(newP, reply.getParentComment(), "Parent ei vaihtunut");
    }

}
