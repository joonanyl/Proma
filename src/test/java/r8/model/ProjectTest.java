package r8.model;

import org.junit.jupiter.api.*;
import r8.model.task.Task;
import r8.model.task.TaskState;
import r8.model.task.TaskType;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author sanku
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectTest {
    /**
     * A project that will be used throughout ProjectTesting
     */
    private static Project project1;
    /**
     * Accounts  that will be used throughout ProjectTesting
     */
    private static Account account1, account2, account3;

    /**
     * BeforeAll-method that initializes the project and accounts required for testing
     */
    @BeforeAll
    static void setUpBeforeTesting() {
        project1 = new Project("projektin nimi1", "desc1");

        account1 = new Account("etunimi", "sukunimi", "email", "pwd");
        account2 = new Account("etunimi2", "sukunimi2", "email", "pwd");
        account3 = new Account("etunimi3", "sukunimi3",  "email",  "pwd");
    }

    /**
     * Test case where getting a set of accounts assigned to a project is being tested
     * @throws Exception
     */
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

    /**
     * Test case where assigning multiple ( a set of ) accounts to a project is being tested
     */
    @Test
    @Order(2)
    void setAccounts() {
        Set<Account> accounts = new HashSet<Account>();
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);

        project1.setAccounts(accounts);

        assertEquals(accounts.size(), project1.getAccounts().size(), "Projektin työskentelijöiden määrässä ongelma");
    }

    /**
     * Test case where gettin a project's name is being tested
     */
    @Test
    @Order(3)
    void getName() {
        assertEquals("projektin nimi1", project1.getName(), "Virheellinen projektin nimi");
    }

    /**
     * Test case where setting a name to a project is being tested
     */
    @Test
    @Order(4)
    void setName() {
        String newName = "Muutettu projektin nimi";
        project1.setName(newName);

        assertEquals(newName, project1.getName(), "Projektin nimen muutos epäonnistui");
    }

    /**
     * Test case where getting a projects description is being tested
     */
    @Test
    @Order(5)
    void getDescription() {
        String desc = "desc1";
        assertEquals(desc, project1.getDescription(), "Palautettu desc virheellinen");
    }

    /**
     * Test case where setting and changing a projects description is being tested
     */
    @Test
    @Order(6)
    void setDescription() {
        String newDesc = "Uusi description";
        project1.setDescription(newDesc);
        assertEquals(newDesc, project1.getDescription(), "Uuden descin asettamisessa virhe");
    }

    /**
     * Test cases where setting, getting and removing tasks from a project are being tested
     */
    @Test
    @Order(7)
    void setAndGetAndRemoveTasks() {
        Set<Task> tasks = new HashSet<>();
        Task task1 = new Task();
        Task task2 = new Task();
        Task task3 = new Task();
        Task task4 = new Task();

        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        project1.setTasks(tasks);

        assertEquals(tasks, project1.getTasks(), "projektille monen tehtävän lisääminen kerrallaan epäonnistui");

        project1.addTask(task4);

        Set<Task> prTasks = project1.getTasks();
        boolean hasT4 = false;

        for(Task t : prTasks){
            if(t == task4)
                hasT4 = true;
        }

        assertTrue(hasT4, "Task4 ei löytynyt projektin taskeista");

        project1.removeTask(task4);
        prTasks = project1.getTasks();
        hasT4 = false; // should stay false

        for(Task t : prTasks){
            if(t == task4)
                hasT4 = true;
        }
        assertFalse(hasT4, "Task4 löytyi projektin taskeista, vaikka sen piti poistua");

    }

    /**
     * Test cases where adding, getting and removing teams from a project are being tested
     */
    @Test
    @Order(8)
    void addAndGetAndRemoveTeams(){
        Team t1 = new Team();
        Team t2 = new Team();

        project1.addTeam(t1);
        project1.addTeam(t2);

        assertTrue(project1.getTeams().contains(t1), "Team1 ei löytynyt projektin tiimilistalta");
        assertTrue(project1.getTeams().contains(t2), "Team2 ei löytynyt projektin tiimilistalta");

        project1.removeTeam(t1);

        assertFalse(project1.getTeams().contains(t1), "Team1 löytynyt projektin tiimilistalta, vaikka Team1 oli poistettu");
    }

}
