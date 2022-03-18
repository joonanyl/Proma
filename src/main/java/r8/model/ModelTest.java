package r8.model;


import org.hibernate.Hibernate;
import r8.model.dao.*;

import java.util.List;
import java.util.Scanner;

public class ModelTest {

    public static void main(String[] args) {
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
        /*
        List<Team> teamsPId1 = teamDAO.getByProject(projectDAO.get(1));
        for (Team t: teamsPId1) {
            System.out.println(t);
        }
        */
        /*
        System.out.println(accountDAO.checkIfEmailExists("testi@sposti.com"));
        System.out.println(accountDAO.checkIfEmailExists("askdöka"));
         */

        /*
        project3.addAccount(account);
        project4.addAccount(account);
        projectDAO.update(project3);
        projectDAO.update(project4);
         */

        /*
        List<Project> projects = projectDAO.getByAccount(accountDAO.get(3));
        for (Project p: projects) {
            System.out.println(p);
        }
         */

        /*
        Team team = teamDAO.get(3);
        Account account = new Account("Lisko", "Mies", "posti@sahko.fi", "passu");
        teamDAO.addAccount(account, team);
         */
        //teamDAO.addAccount(new Account("Tiimi", "Lisäys", "testi@gmail.com", "salis"),
        //        new Team("Team 4", projectDAO.get(2)));
        // teamDAO.removeAccountAssociation(team, accountDAO.get(13));

        /*
        Project project = projectDAO.get(2);
        project.setName("Full Stack Open");
        project.setDescription("Full stack web development course.");
        projectDAO.update(project);
         */
        projectDAO.removeAccountAssociation(accountDAO.get(14), projectDAO.get(2));
    }
}
