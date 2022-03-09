package r8.model.task;

import org.junit.jupiter.api.*;
import r8.model.Account;
import r8.model.Project;
import r8.model.Team;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    private static Task task;
    private static TaskType taskType;
    private static Project project;
    private static Team team;
    private static Account account;

    @BeforeAll
    static void setUpBeforeTesting() {
        taskType = new TaskType("test");
        task = new Task("task", TaskState.NOT_STARTED, taskType, 48, "desc");
        project = new Project("project", "desc");
        team = new Team("team", project);
        account = new Account("etunimi", "sukunimi", "email", "pwd");
    }

    @Test
    void assignToTeam() {
        task.assignToTeam(team);
        assertTrue(team.getTasks().contains(task), "Taskin lisääminen tiimille epäonnistui (ei löydy tiimin task-listasta)");
        assertTrue(task.getTeams().contains(team), "Taskin lisääminen tiimille epäonnistui (ei löytynyt omasta tiimi-listasta)");
    }


    @Test
    void removeFromTeam() {
        task.removeFromTeam(team);
        assertFalse(team.getTasks().contains(task), "Taskin poistaminen tiimiltä epäonnistui (löytyy tiimin task-listasta)");
        assertFalse(task.getTeams().contains(team), "Taskin poistaminen tiimilte epäonnistui (löytyy omasta tiimi-listasta)");
    }

    @Test
    void assignAccount() {
        task.assignAccount(account);
        assertTrue(account.getTasks().contains(task), "Taskin lisääminen käyttäjätilille epäonnistui");
        assertTrue(task.getAccounts().contains(account), "Käyttäjätilin lisääminen taskin listään ");
    }

    @Disabled
    @Test
    void removeAccount() {
    }

    @Disabled
    @Test
    void getTaskId() {
    }

    @Disabled
    @Test
    void setTaskId() {
    }

    @Disabled
    @Test
    void getName() {
    }

    @Disabled
    @Test
    void setName() {
    }

    @Disabled
    @Test
    void getTaskState() {
    }

    @Disabled
    @Test
    void setTaskState() {
    }

    @Disabled
    @Test
    void getTaskStateString() {
    }

    @Disabled
    @Test
    void setTaskStateString() {
    }

    @Disabled
    @Test
    void getTaskType() {
    }

    @Disabled
    @Test
    void setTaskType() {
    }

    @Disabled
    @Test
    void getDescription() {
    }

    @Disabled
    @Test
    void setDescription() {
    }

    @Disabled
    @Test
    void getHours() {
    }

    @Disabled
    @Test
    void setHours() {
    }

    @Disabled
    @Test
    void getStartDate() {
    }

    @Disabled
    @Test
    void setStartDate() {
    }

    @Disabled
    @Test
    void getEndDate() {
    }

    @Disabled
    @Test
    void setEndDate() {
    }

    @Disabled
    @Test
    void getAccounts() {
    }

    @Disabled
    @Test
    void setAccounts() {
    }

    @Disabled
    @Test
    void getSprint() {
    }

    @Disabled
    @Test
    void setSprint() {
    }

    @Disabled
    @Test
    void getProject() {
    }

    @Disabled
    @Test
    void setProject() {
    }

    @Disabled
    @Test
    void getTeams() {
    }

    @Disabled
    @Test
    void setTeams() {
    }

}