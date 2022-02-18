package r8.model;


import r8.model.dao.AccountDAO;
import r8.model.dao.ProjectDAO;
import r8.model.task.Task;
import r8.model.task.TaskState;
import r8.model.task.TaskType;

public class ModelTest {

    public static void main(String args[]) {

        ProjectDAO projectDAO = new ProjectDAO();
        AccountDAO accountDAO = new AccountDAO();

        Project project = new Project("Testiprojekti", "a test project.", "5000");
        Account account = new Account("Käyttäjä", "Testi", "014140002", "testi@testi123.fi");

        project.addAccount(account);
        projectDAO.addProject(project);

        project.removeAccount(account);


        System.out.println(account.getAccountId());
        System.out.println(project.getProjectId());

        /*
        Project project = new Project("Testiprojecti", "1000€");
        Team team = new Team("Team 1");
        Account account = new Account("Testi", "Käyttäjä", "0401234567", "testi@testi.com");

        team.addMember(account);
        System.out.println(team.getMembers());
        project.addTeam(team);
        System.out.println("Projekti: " + project.getTeamsList());

        Task task = new Task("Testitask", TaskState.NOT_STARTED, TaskType.MEETING);
        task.assignToTeam(team.getTeamId());
        // Tämän jälkeen hibernatella yhteydet kuntoon
         */
    }
}
