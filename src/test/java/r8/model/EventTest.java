package r8.model;

import org.junit.jupiter.api.*;
import r8.model.task.Task;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author sanku
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventTest {
    /**
     * An event that will be used throughout EventTesting
     */
    private static Event event;
    /**
     * A task that is required for initializing an event
     */
    private static Task task;
    /**
     * An account that is required for initializing an event
     */
    private static Account account;

    /**
     * BeforeAll-method that initializes the task, account and event that are required for testing
     */
    @BeforeAll
    static void setUpBeforeTesting() {
        task = new Task();
        account = new Account("etunimi", "sukunimi", "email", "pwd");
        event = new Event("desc", LocalDate.now(), 2, account);
    }

    /**
     * TestCase where setting and getting an event's description is being tested
     */
    @Test
    @Order(1)
    void setAdnGetDescription() {
        String oldDesc = event.getDescription();
        String newDesc = "new desc";

        event.setDescription(newDesc);
        assertEquals(newDesc, event.getDescription(), "Descin muuttaminen epäonnistui");
        assertNotEquals(oldDesc, event.getDescription(), "Descin muuttaminen epäonnistui (2)");
        assertNotEquals("desc", event.getDescription(), "Descin muuttaminen epäonnistui (3)");
    }

    /**
     * Test case where setting and getting an event's date is being tested
     */
    @Test
    @Order(2)
    void setAndGetDate() {
        assertEquals(LocalDate.now(), event.getDate(), "Päivämäärän hakeminen epäonnistui");
        event.setDate(LocalDate.now().plusDays(5));
        assertEquals(LocalDate.now().plusDays(5), event.getDate(), "Päivämäärän muuttaminen epäonnistui");
        assertNotEquals(LocalDate.now(), event.getDate(), "Päivämäärän muuttaminen epäonnistui(2)");
    }

    /**
     * Test case where setting and getting the working hours of an event is being tested
     */
    @Test
    @Order(3)
    void setAndGetHours() {
        float h = event.getHours();
        event.setHours(h + 2);
        float newH = h + 2;
        assertEquals(newH, event.getHours(), "Tuntimäärän muuttaminen epäonnistui");
        assertNotEquals(h, event.getHours(), "Tuntimäärän muuttaminen epäonnistui (2)");
    }

    /**
     * Test case where setting and getting an account assigned to an event is being tested
     */
    @Test
    @Order(4)
    void setAndGetAccount() {
        event.setAccount(account);
        Account a2 = new Account("a", "b", "c", "d");
        assertEquals(account, event.getAccount(), "Käyttäjätilin asetttaminen epäonnistui");
        assertNotEquals(a2.getFirstName(), event.getAccount().getFirstName(), "Palautti väärän käyttäjätilin");
        event.setAccount(a2);
        assertEquals(a2.getFirstName(), event.getAccount().getFirstName(), "Käyttäjätilin muuttaminen epäonnistui");
    }

    /**
     * Test case where setting and getting a task assigned to an event is being tested
     */
    @Test
    @Order(5)
    void setAndGetTask() {
        event.setTask(task);
        assertEquals(task, event.getTask(), "Tehtävän hakeminen epäonnistui");
        Task t2 = new Task();
        t2.setName("Task2");
        event.setTask(t2);
        assertEquals(t2, event.getTask(), "Taskin vaihtaminen epäonnistui");
        assertNotEquals(task, event.getTask(), "Taskin vaihtaminen epäonnistui (2)");
    }

    /**
     * Test case where event's second constructor is being tested
     */
    @Test
    @Order(7)
    void scndConstructor(){
        String d = "desc";
        LocalDate ld = LocalDate.now();
        float h = 30;
        Account a = new Account();
        a.setFirstName("account");
        Event e = new Event(d, ld, h, a);

        assertEquals(d, e.getDescription(), "Desc ei täsmää");
        assertEquals(ld, e.getDate(), "Pvm ei täsmää");
        assertEquals(h, e.getHours(), "Tunnit eivät täsmää");
        assertEquals(a.getFirstName(), e.getAccount().getFirstName(), "Account ei täsmää");

    }

    /**
     * Test case where event's third constructor is being tested
     */
    @Test
    @Order(8)
    void thrdContructor(){
        String d = "desc";
        LocalDate ld = LocalDate.now();
        float h = 30;
        Account a = new Account();
        Task t = new Task();
        a.setFirstName("account");
        Event e = new Event(d, ld, h, a, t);

        assertEquals(d, e.getDescription(), "Desc ei täsmää");
        assertEquals(ld, e.getDate(), "Pvm ei täsmää");
        assertEquals(h, e.getHours(), "Tunnit eivät täsmää");
        assertEquals(a.getFirstName(), e.getAccount().getFirstName(), "Account ei täsmää");
        assertEquals(t, e.getTask(), "Task ei täsmää");

    }

    /**
     * Test case where event's fourth contsructor is being tested
     */
    @Test
    @Order(9)
    void frthContructor() {
        String d = "desc";
        LocalDate ld = LocalDate.now();
        float h = 30;
        Account a = new Account();
        Task t = new Task();
        Project p = new Project();
        a.setFirstName("account");
        Event e = new Event(d, ld, h, a, t, p);

        assertEquals(d, e.getDescription(), "Desc ei täsmää");
        assertEquals(ld, e.getDate(), "Pvm ei täsmää");
        assertEquals(h, e.getHours(), "Tunnit eivät täsmää");
        assertEquals(a.getFirstName(), e.getAccount().getFirstName(), "Account ei täsmää");
        assertEquals(t, e.getTask(), "Task ei täsmää");
        assertEquals(p, e.getProject(), "Project ei täsmää");

    }

    /**
     * Test case where event's fifth contsructor is being tested
     */
    @Test
    @Order(10)
    void ffthContructor(){
        int eId = 1;
        String d = "desc";
        LocalDate ld = LocalDate.now();
        float h = 30;
        Account a = new Account();
        Task t = new Task();
        Project p = new Project();
        Sprint s = new Sprint();
        a.setFirstName("account");
        Event e = new Event(eId, d, ld, h, a, t, p, s);

        assertEquals(eId, e.getEventId(), "Id ei täsmää");
        assertEquals(d, e.getDescription(), "Desc ei täsmää");
        assertEquals(ld, e.getDate(), "Pvm ei täsmää");
        assertEquals(h, e.getHours(), "Tunnit eivät täsmää");
        assertEquals(a.getFirstName(), e.getAccount().getFirstName(), "Account ei täsmää");
        assertEquals(t, e.getTask(), "Task ei täsmää");
        assertEquals(p, e.getProject(), "Project ei täsmää");
        assertEquals(s, e.getSprint(), "Sprint ei täsmää");
    }


}
