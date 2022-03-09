package r8.controller;

import r8.model.*;
import r8.model.appState.AppState;
import r8.model.dao.*;

import java.util.List;

public class Controller {
    private AccountDAO accountDAO;
    private CommentDAO commentDAO;
    private EventDAO eventDAO;
    private ProjectDAO projectDAO;
    private SprintDAO sprintDAO;
    private TaskDAO taskDAO;
    private TaskTypeDAO taskTypeDAO;
    private TeamDAO teamDAO;
    private AppState appState;

    public Controller(AppState appstate) {
        this.accountDAO = new AccountDAO();
        this.commentDAO = new CommentDAO();
        this.eventDAO = new EventDAO();
        this.projectDAO = new ProjectDAO();
        this.sprintDAO = new SprintDAO();
        this.taskDAO = new TaskDAO();
        this.taskTypeDAO = new TaskTypeDAO();
        this.teamDAO = new TeamDAO();
        this.appState = appstate;
    }

    public boolean authenticateLogin(String email, String password) {
        boolean authentication = AuthService.authenticatePassword(email, password);
        if (authentication) {
            return true;
        }
        return false;
    }

    public void logout() {
        appState.setLoggedAccount(null);
    }

    public void createAccount(String firstName, String lastName, String email, String password) {
        Account account = new Account(firstName, lastName, email, password);
        accountDAO.persist(account);
    }

    public Account getAccount(Integer accountId) {
        Account account = accountDAO.get(accountId);
        return account;
    }

    public Account getAccountByEmail(String email) {
        Account account = accountDAO.getByEmail(email);
        return account;
    }

    public void updateAccount(String firstName, String lastName, String email, String password) {
        Account account = accountDAO.getByEmail(AppState.getInstance().getLoggedAccount().getEmail());
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(email);
        account.setPassword(AuthService.hashPassword(password));
        accountDAO.update(account);
    }

    public void removeAccount(Account account) {
        accountDAO.removeAccount(account);
    }

    public void createProject(String name, String description) {
        Project project = new Project(name, description);
        projectDAO.persist(project);
    }

    public Project getProjectById(int projectId) {
        Project project = projectDAO.get(projectId);
        return project;
    }

    public Project getProjectByName(String name) {
        Project project = projectDAO.getByName(name);
        return project;
    }

    public void updateProject(int projectId, String name, String description) {
        Project project = projectDAO.get(projectId);
        project.setName(name);
        project.setDescription(description);
        projectDAO.update(project);
    }

    public void removeProject(Project project) {
        projectDAO.removeProject(project);
    }

    public Project loadProject() {
        return null;
    }

    // Tai sitten ui-controllerissa kutsuisi jo parametrissä
    // controller.getProjectById()
    public void createTeam(String teamName, int projectId) {
        Team team = new Team(teamName, projectDAO.get(projectId));
        teamDAO.persist(team);
    }

    public Team getTeamById(int teamId) {
        Team team = teamDAO.get(teamId);
        return team;
    }

    public Team getTeamByName(String name) {
        Team team = teamDAO.getByName(name);
        return team;
    }

    public void updateTeam(int teamId, String name, int projectId) {
        Team team = teamDAO.get(teamId);
        team.setTeamName(name);
        team.setProject(projectDAO.get(projectId));
    }

    public void removeTeam(Team team) {
        teamDAO.removeTeam(team);
    }
}
