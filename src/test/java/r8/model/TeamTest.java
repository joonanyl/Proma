package r8.model;

import org.junit.jupiter.api.*;
import r8.model.task.Task;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TeamTest {

    private static Team team1;
    private static Project project1;
    private static Account account1, account2, account3, account4;
    private static Task task1, task2, task3;

    @BeforeAll
    static void setUpBeforeTesting() {
        team1 = new Team();
        project1 = new Project("project1", "desc");
    }

    @Test
    @Order(1)
    void setTeamName() {
        String newName = "team1";
        team1.setTeamName(newName);
        assertEquals(newName, team1.getTeamName(), "Uuden tiiminnimen asettaminen epäonnistui");
    }

    @Test
    @Order(2)
    void setAndSetProject() {
        team1.setProject(project1);
        assertEquals(project1, team1.getProject(), "Projektin asettamisessa ongelmia (1)");

        Project project2 = new Project("project2", "desc2");
        Team team2 = new Team("team2", project2);

        assertEquals(project2, team2.getProject(), "Projektin asettamisessa ongelmia (2)");

        team2.setProject(project1);
        assertEquals(project1, team2.getProject(), "Projektin vaihtamisessa ongelmia");
    }

    @Test
    @Order(3)
    void setAndSetAccounts() {

        account1 = new Account("etunimi", "sukunimi","email",  "pwd");
        account2 = new Account("etunimi2", "sukunimi2",  "email",  "pwd");
        account3 = new Account("etunimi3", "sukunimi3",  "email",  "pwd");

        Set<Account> accounts = new HashSet<Account>();
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);

        team1.setAccounts(accounts);

        assertEquals(accounts, team1.getAccounts(), "Käyttäjätililistat eivät täsmää - Lisääminen epäonnistui");
    }

    @Test
    @Order(4)
    void getAccountByName() {
        String fName = account1.getFirstName();
        String lName = account1.getLastName();

        assertEquals(account1, team1.getAccountByName(fName, lName), "Käyttäjän hakeminen nimen mukaan epäonnistui");
    }

    @Test
    @Order(5)
    void addAccount() {

        account4 = new Account("etunimi4", "sukunimi4", "email",  "pwd");

        team1.addAccount(account4);

        assertTrue(team1.getAccounts().contains(account4), "Yksittäisen työntekijän lisääminen tiimiin epäonnistui");
    }

    @Test
    @Order(6)
    void removeAccount() {
        team1.removeAccount(account4);
        assertFalse(team1.getAccounts().contains(account4), "Työntekijän poistaminen tiimistä epäonnistui");
    }

    @Test
    @Order(7)
    void setAndGetTasks() {
        task1 = new Task();
        task2 = new Task();
        task3 = new Task();

        Set<Task> tasks = new HashSet<Task>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        team1.setTasks(tasks);

        assertEquals(tasks, team1.getTasks(), "Työtehtävälistat eivät täsmää");
    }

}
