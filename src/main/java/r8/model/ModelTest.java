package r8.model;


import r8.model.dao.*;
import r8.model.task.Task;
import r8.model.task.TaskState;
import r8.model.task.TaskType;

import java.time.LocalDate;
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
        /*
        Account account = new Account("Login", "Testi", "044400112", "testi@sposti.com",
            "joona", AuthService.hashPassword("salasana"));
        accountDAO.persist(account);
        */

    /*
        String verifier = accountDAO.getLoginInfo("joona");
        boolean login = AuthService.authenticatePassword("joona", "xd", verifier);

     */
        TaskDAO taskDAO = new TaskDAO();
        TaskType taskType = taskTypeDAO.get(1);
        Task task = new Task("Test task", TaskState.NOT_STARTED, taskType, 1f, "Testing tasks.");
        task.setProject(projectDAO.getProject(2));
        taskDAO.persist(task);
        Event event = new Event("Progress on bug task.", LocalDate.now(), 1.5f, task, accountDAO.get(1));
        eventDAO.addEvent(event);
    }
}
