package r8.model;

import org.junit.jupiter.api.*;
import r8.model.task.Task;

import javax.swing.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author sanku
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountTest {
    /**
     * Accounts that will be used thoughout AccountTesting
     */
    static Account account, admin;
    /**
     * Projects that will be used thoughout AccountTesting
     */
    static Project project, project2;
    /**
     * Teams that will be used thoughout AccountTesting
     */
    static Team team, team2;

    /**
     * BeforeAll-method that initializes an account required for testing
     */
    @BeforeAll
    static void setUpBeforeTesting(){
        account = new Account("fname", "lname", "email", "pwd");
    }

    /**
     * Test cases where an accounts setters/getters are being tested
     */
    @Test
    @Order(1)
    void basics(){
        assertEquals("fname", account.getFirstName(), "Etunimi ei täsmää");
        assertEquals("lname", account.getLastName(), "Sukunimi ei täsmää");
        assertEquals("email", account.getEmail(), "Email ei täsmää");
        account.setFirstName("etunimi");
        account.setLastName("sukunimi");
        account.setEmail("sposti");
        assertEquals("etunimi", account.getFirstName(), "Etunimi ei täsmää muutoksen jälkeen");
        assertEquals("sukunimi", account.getLastName(), "Sukunimi ei täsmää muutoksen jälkeen");
        assertEquals("sposti", account.getEmail(), "Sposti ei täsmää muutoksen jälkeen");
        String oldPwd = account.getPassword();
        account.setPassword("new pwd");
        assertNotEquals(oldPwd, account.getPassword(), "Salasana ei muuttunut");
    }

    /**
     * Test case where assigning a set of projects to an account is being tested
     */
    @Test
    @Order(2)
    void projects(){
        project = new Project();
        project.setName("projekti");
        project2 = new Project();
        project2.setName("projekti2");
        Set<Project> projects = new HashSet<>();
        projects.add(project);
        projects.add(project2);
        account.setProjects(projects);
        assertEquals(projects, account.getProjects(), "projektit eivät täsmää");
    }

    /**
     * Test case where assigning a set of teams to an account is being tested
     */
    @Test
    @Order(3)
    void teams(){
        team = new Team();
        team2 = new Team();
        team.setTeamName("team1");
        team2.setTeamName("team2");
        Set<Team> teams = new HashSet<>();
        teams.add(team);
        teams.add(team2);
        account.setTeams(teams);
        assertEquals(teams, account.getTeams(), "Tiimit eivät täsmää");
    }

    /**
     * Test case where setting an account as an admin is being tested
     */
    @Test
    @Order(4)
    void admin(){
        admin = new Account();
        admin.setFirstName("admin");
        admin.setAdmin(true);
        assertTrue(admin.getAdmin(), "Käyttäjätili ei ole admin");
        assertFalse(account.getAdmin(), "Käyttäjätili ei pitäisi olla admin");
    }

    /**
     * Test case where setting a set of events to an account is being tested
     */
    @Test
    @Order(5)
    void events(){
        Event e = new Event();
        Event e2 = new Event();
        Set<Event> events = new HashSet<>();
        events.add(e);
        events.add(e2);
        account.setEvents(events);
        assertEquals(events, account.getEvents(), "Eventit eivät täsmää");
    }

    /**
     * Test case where assigning a set of tasks to an account is being tested
     */
    @Test
    @Order(6)
    void tasks(){
        Task t = new Task();
        Task t2 = new Task();
        Task t3 = new Task();

        Set<Task> tasks = new HashSet<>();
        tasks.add(t);
        tasks.add(t2);
        account.setTasks(tasks);

        assertEquals(tasks, account.getTasks(), "Taskit eivät täsmää");
        account.setTask(t3);
        assertTrue(account.getTasks().contains(t3), "Task3 ei löydy tasklistasta käyttäjätilillä");
    }

}
