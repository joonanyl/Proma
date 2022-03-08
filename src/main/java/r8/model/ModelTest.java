package r8.model;


import r8.model.dao.*;

import java.util.List;
import java.util.Scanner;

public class ModelTest {

    public static void main(String args[]) {
        /*
        AccountDAO accountDAO = new AccountDAO();
        TeamDAO teamDAO = new TeamDAO();
        ProjectDAO projectDAO = new ProjectDAO();

        Project project1 = new Project("Projektitesti 2", "Test project with teams.", "2");
        Team team1 = new Team();
        Account account = new Account("Test", "Account", "014412312", "test@test.com");
        Account account2 = new Account("Testi", "Käyttäjä", "112311332", "testi@sposti.fi");

        Project project = projectDAO.getProject(1);

        team1.setTeamName("Testitiimi");
        team1.setProject(project);

        team1.addAccount(account);
        team1.addAccount(account2);

        teamDAO.addTeam(team1);
        */

        Scanner scanner = new Scanner(System.in);
        AccountDAO accountDAO = new AccountDAO();
        EventDAO eventDAO = new EventDAO();
        ProjectDAO projectDAO = new ProjectDAO();
        TaskTypeDAO taskTypeDAO = new TaskTypeDAO();
        TeamDAO teamDAO = new TeamDAO();

        /*
        TaskDAO taskDAO = new TaskDAO();
        TaskType taskType = taskTypeDAO.get(1);
        Task task = new Task("Test task", TaskState.NOT_STARTED, taskType, 1f, "Testing tasks.");
        task.setProject(projectDAO.getProject(2));
        taskDAO.persist(task);
        Event event = new Event("Progress on bug task.", LocalDate.now(), 1.5f, task, accountDAO.get(1));
        eventDAO.addEvent(event);
         */

        /*
        Account account = new Account("Joona", "Nylander", "testiposti@testi.fi", "salis");
        accountDAO.persist(account);
         */

        /*
        boolean login = AuthService.authenticatePassword("joona@testi.fi", "salis");
        System.out.println(login);
        List<Account> results = accountDAO.getAll();
        for (Account a: results
             ) {
            System.out.println(a);
        }

        System.out.println(accountDAO.getByEmail("joona@testi.fi"));
        */


        //Team team = new Team("Testitiimi", projectDAO.get(1));
        //teamDAO.persist(team);

        List<Team> teamsPId1 = teamDAO.getByProject(projectDAO.get(1));
        for (Team t: teamsPId1) {
            System.out.println(t);
        }
    }
}
