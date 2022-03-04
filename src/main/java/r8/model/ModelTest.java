package r8.model;


import org.mindrot.jbcrypt.BCrypt;
import r8.model.dao.AccountDAO;
import r8.model.dao.DAO;
import r8.model.dao.ProjectDAO;
import r8.model.dao.TeamDAO;
import r8.model.task.Task;
import r8.model.task.TaskState;
import r8.model.task.TaskType;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Scanner;

public class ModelTest {

    public static void main(String args[]) throws NoSuchAlgorithmException, NoSuchProviderException {
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

        // Account account = new Account("Login", "Testi", "044400112", "testi@sposti.com",
           //     "joona", AuthService.hashPassword("salasana"));
        // accountDAO.addAccount(account);

        String verifier = accountDAO.getLoginInfo("joona");
        boolean login = AuthService.authenticatePassword("joona", "xd", verifier);
        System.out.println(login);
    }
}
