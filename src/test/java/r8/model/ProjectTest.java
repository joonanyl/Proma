package r8.model;

import org.junit.jupiter.api.*;
import r8.model.task.Task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectTest {

    private static Project project1;
    private static Account account1, account2, account3;

    @BeforeAll
    static void setUpBeforeTesting() {
        project1 = new Project("projektin nimi1", "desc1");

        account1 = new Account("etunimi", "sukunimi", "email", "pwd");
        account2 = new Account("etunimi2", "sukunimi2", "email", "pwd");
        account3 = new Account("etunimi3", "sukunimi3",  "email",  "pwd");
    }

    @Test
    @Order(1)
    void getAccounts() throws Exception{
        Set<Account> accounts = new HashSet<Account>();
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);

        project1.setAccounts(accounts);

        assertEquals(accounts, project1.getAccounts(), "Projektin käyttäjätilit monikossa lisätty epäonnistuneesti");
    }

    @Test
    @Order(2)
    void setAccounts() {
        Set<Account> accounts = new HashSet<Account>();
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);

        project1.setAccounts(accounts);

        assertEquals(3, project1.getAccounts().size(), "Projektin työskentelijöiden määrässä ongelma");
    }

    @Test
    @Order(3)
    void getName() {
        assertEquals("projektin nimi1", project1.getName(), "Virheellinen projektin nimi");
    }

    @Test
    @Order(4)
    void setName() {
        String newName = "Muutettu projektin nimi";
        project1.setName(newName);

        assertEquals(newName, project1.getName(), "Projektin nimen muutos epäonnistui");
    }

    @Test
    @Order(7)
    void getDescription() {
        String desc = "desc1";
        assertEquals(desc, project1.getDescription(), "Palautettu desc virheellinen");
    }

    @Test
    @Order(8)
    void setDescription() {
        String newDesc = "Uusi description";
        project1.setDescription(newDesc);
        assertEquals(newDesc, project1.getDescription(), "Uuden descin asettamisessa virhe");
    }

    @Test
    @Order(9)
    void getTasks() {
        Set<Task> tasks = new HashSet<>();
        Task task1 = new Task();
        Task task2 = new Task();
        Task task3 = new Task();

        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        project1.setTasks(tasks);

        assertEquals(tasks, project1.getTasks(), "projektille monen tehtävän lisääminen kerrallaan epäonnistui");
    }

}