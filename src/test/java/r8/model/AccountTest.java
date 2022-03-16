package r8.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import r8.model.task.Task;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private static Account account1;
    private static Project project1, project2, project3;
    private static Team team1, team2, team3;
    private static Task task1, task2, task3;

    @BeforeAll
    static void setUpBeforeTesting() throws Exception{

        account1 = new Account("etunimi", "sukunimi", "email", "pwd");
        project1 = new Project("project1 name", "desc1");
        project2 = new Project("project2 name", "desc2");
        project3 = new Project("project3 name", "desc3");

        team1 = new Team("tiimi1", project1);
        team2 = new Team("tiimi2", project2);
        team3 = new Team("tiimi3", project3);

        task1 = new Task();
        task2 = new Task();
        task3 = new Task();

    }

    @Test
    void removeFromProject() {
        project1.addAccount(account1);

        account1.removeFromProject(project1);

        assertFalse(project1.getAccounts().contains(account1), "Käyttäjätili ei poistunut projektin työntekijälistasta");
    }

    @Test
    void getProjects() {
        project1.addAccount(account1);
        project2.addAccount(account1);
        project3.addAccount(account1);

        assertTrue(account1.getProjects().contains(project1), "Käyttäjätilin projektilistalta puuttuu projekti 1");
        assertTrue(account1.getProjects().contains(project2), "Käyttäjätilin projektilistalta puuttuu projekti 2");
        assertTrue(account1.getProjects().contains(project3), "Käyttäjätilin projektilistalta puuttuu projekti 3");

        assertEquals(3, account1.getProjects().size(), "Projektilista väärän kokoinen");
    }

    @Test
    void setProjects() {
        Set<Project> projects = new HashSet<Project>();
        projects.add(project1);
        projects.add(project2);
        projects.add(project3);

        account1.setProjects(projects);

        assertEquals(3, account1.getProjects().size(), "Käyttäjätilillä väärä määrä projekteja");
        assertTrue(account1.getProjects().contains(project1), "Käyttäjätililtä ei löydy project1");
        assertTrue(account1.getProjects().contains(project2), "Käyttäjätililtä ei löydy project2");
        assertTrue(account1.getProjects().contains(project3), "Käyttäjätililtä ei löydy project3");
    }

    @Test
    void getFirstName() {
        assertEquals("etunimi", account1.getFirstName(), "Käyttäjätilin etunimi palautui väärin");
    }

    @Test
    void getLastName() {
        assertEquals("sukunimi", account1.getLastName(), "Käyttäjätilin sukunimi palautui väärin");
    }

    @Test
    void getTeams() {
        Set<Team> teams = new HashSet<Team>();
        teams.add(team1);
        teams.add(team2);
        teams.add(team3);

        account1.setTeams(teams);

        assertEquals(3, account1.getTeams().size(), "Käyttäjätilillä väärä määrä tiimejä");
        assertTrue(account1.getTeams().contains(team1), "Käyttäjätililtä ei löydy team1");
        assertTrue(account1.getTeams().contains(team2), "Käyttäjätililtä ei löydy team2");
        assertTrue(account1.getTeams().contains(team3), "Käyttäjätililtä ei löydy team3");
    }

    @Test
    void setTeams() {
        Set<Team> teams = new HashSet<Team>();
        teams.add(team1);
        teams.add(team2);
        teams.add(team3);

        account1.setTeams(teams);

        assertEquals(3, account1.getTeams().size(), "Tiimimäärä väärin");
    }


    @Test
    void getTasks() {
        Set<Task> tasks = new HashSet<Task>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        account1.setTasks(tasks);

        assertEquals(3, account1.getTasks().size(), "Käyttäjän tehtävien määrä virheellinen");
    }

    @Test
    void setTask() {
        Set<Task> tasks = new HashSet<Task>();
        tasks.add(task1);
        tasks.add(task2);

        System.out.println("tasks size = " + tasks.size());

        account1.setTasks(tasks);
        assertEquals(2, account1.getTasks().size(), "Käyttäjän tehtävien määrä virheellinen");

        account1.setTask(task3);
        assertEquals(3, account1.getTasks().size(), "Käyttäjän tehtävien määrä virheellinen");

        account1.setTask(task3);
        assertEquals(3, account1.getTasks().size(), "Duplikaatti tehtävä");
    }

}