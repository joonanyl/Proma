package r8.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
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
        account1 = new Account("etunimi", "sukunimi", "123", "email", "login", "pwd");
        project1 = new Project("project1 name", "desc1", "budjetti1");
        project2 = new Project("project2 name", "desc2", "budjetti2");
        project3 = new Project("project3 name", "desc3", "budjetti3");

        team1 = new Team("tiimi1", project1);
        team2 = new Team("tiimi2", project2);
        team3 = new Team("tiimi3", project3);
    }

    @Test
    void assignToProject() {
        account1.assignToProject(project1);
        Set<Account> testResult = project1.getAccounts();

        assertTrue(testResult.contains(account1), "Projektin työskenelijälistasta ei löytynyt lisättyä käyttäjätiliä");
    }

    @Test
    void removeFromProject() {
        account1.assignToProject(project1);
        account1.assignToProject(project2);
        account1.assignToProject(project3);

        account1.removeFromProject(project1);

        assertEquals(2, account1.getProjects().size(), "Käyttäjätilillä väärä määrä projekteja");
    }

    @Test
    void getProjects() {
        account1.assignToProject(project1);
        account1.assignToProject(project2);
        account1.assignToProject(project3);

        assertEquals(3, account1.getProjects().size(), "Käyttäjätilillä väärä määrä projekteja");
        assertTrue(account1.getProjects().contains(project1), "Käyttäjätililtä ei löydy project1");
        assertTrue(account1.getProjects().contains(project2), "Käyttäjätililtä ei löydy project2");
        assertTrue(account1.getProjects().contains(project3), "Käyttäjätililtä ei löydy project3");
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
/*
    @Disabled
    @Test
    void getTasks() {
    }

    @Disabled
    @Test
    void setTasks() {
    }

    */
}