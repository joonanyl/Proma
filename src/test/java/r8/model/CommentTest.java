package r8.model;

import org.junit.jupiter.api.*;
import r8.model.task.Task;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentTest {

    static Task t;
    static Account a, a2;
    static String c, c2;
    static Comment parent, reply, reply2, reply3;

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

    @Test
    @Order(1)
    void mainContructor() {
        assertEquals(t, parent.getTask(), "Task ei täsmää");
        assertEquals(a.getFirstName(), parent.getAccount().getFirstName(), "Account ei täsmää");
        assertEquals(c, parent.getContent(), "Teksti ei täsmää");
    }

    @Test
    @Order(2)
    void replyConstructor(){
        reply = new Comment(parent, a2, c2);
        assertEquals(a2.getFirstName(), reply.getAccount().getFirstName(), "Account ei täsmää");
        assertEquals(c2, reply.getContent(), "Teksti ei täsmää");
        assertEquals(parent, reply.getParentComment(), "Alkuperäinen kommentti ei täsmää");
    }

    @Test
    @Order(3)
    void editText(){
        String newText = parent.getContent() + " vol2";
        parent.editText(newText);

        assertEquals(newText, parent.getContent(), "Uusi teksti ei päivittynyt");
        assertNotEquals(c, parent.getContent(), "Uusi teksti ei päivittynyt (2)");
    }

    @Test
    @Order(4)
    void addReply(){
        parent.addReply(reply);
        assertTrue(parent.getChildComments().contains(reply), "Vastausta ei löytynyt");
        assertEquals(parent, reply.getParentComment(), "Alkuperäinen kommentti ei täsmää");
    }

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
