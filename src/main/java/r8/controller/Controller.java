package r8.controller;

import javafx.collections.ObservableList;
import r8.model.*;
import r8.model.appState.AppState;
import r8.model.dao.*;
import r8.model.task.Task;
import r8.model.task.TaskState;
import r8.model.task.TaskType;
import r8.view.IViewController;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class Controller implements IControllerLogin, IControllerMain, IControllerAccount {
    private AccountDAO accountDAO;
    private CommentDAO commentDAO;
    private EventDAO eventDAO;
    private ProjectDAO projectDAO;
    private SprintDAO sprintDAO;
    private TaskDAO taskDAO;
    private TaskTypeDAO taskTypeDAO;
    private TeamDAO teamDAO;

    //Does not receive AppState as a parameter anymore
    public Controller() {
        this.accountDAO = new AccountDAO();
        this.commentDAO = new CommentDAO();
        this.eventDAO = new EventDAO();
        this.projectDAO = new ProjectDAO();
        this.sprintDAO = new SprintDAO();
        this.taskDAO = new TaskDAO();
        this.taskTypeDAO = new TaskTypeDAO();
        this.teamDAO = new TeamDAO();
    }

    public boolean authenticateLogin(String email, String password) {
        if (AuthService.authenticatePassword(email, password)) {
            AppState.getInstance().setLoggedAccount(getAccountByEmail(email));
            return true;
        }
        return false;
    }

    public void logout() {
        AppState.getInstance().setLoggedAccount(null);
    }

    public void createAccount(String firstName, String lastName, String email, String password) {
        Account account = new Account(firstName, lastName, email, password);
        accountDAO.persist(account);
    }

    public Account getAccount() {
        return AppState.getInstance().getAccount();
    }

    public Account getAccount(Integer accountId) {
        return accountDAO.get(accountId);
    }

    public Account getAccountByEmail(String email) {
        return accountDAO.getByEmail(email);
    }

    public List<Account> getAllAccounts() {
        return accountDAO.getAll();
    }

    public boolean checkIfEmailExists(String email) {
        return accountDAO.getByEmail(email) != null;
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

    public void createProject(String name, String description, ObservableList<Account> accountList, ObservableList<String> teamList) {
        Project project = new Project(name, description);
        if (accountList != null) {
            accountList.forEach(project::addAccount);
        }
        project.addAccount(accountDAO.get(AppState.getInstance().getLoggedAccount().getAccountId()));
        // Tässä vaiko näkymän latauksessa päivitetään AppStateen projektit?
        projectDAO.persist(project);
        if (teamList != null) {
            teamList.forEach((item) -> {
                createTeam(item, project);
            });
        }
    }

    public Project getProjectById(int projectId) {
        return projectDAO.get(projectId);
    }

    @Override
    public List<Project> getProjects() {
        return null;
    }

    public Project getProjectByName(String name) {
        return projectDAO.getByName(name);
    }

    public List<Project> getProjectByAccount(Account account) {
        return projectDAO.getByAccount(account);
    }

    public List<Project> getAllProjects() {
        return projectDAO.getAll();
    }

    //TEAMS tähän myös, päivitä samalla AppState?
    public void updateProject(Project project, String name, String description, List<Team> teams) {
        project.setName(name);
        project.setDescription(description);
        project.setTeams(teams);
        projectDAO.update(project);
    }

    public void removeProject(Project project) {
        projectDAO.remove(project);
    }

    public List<Project> loadProjects(Account account) {
        return projectDAO.getByAccount(account);
    }

    public void createTeam(String teamName, Project project) {
        Team team = new Team(teamName, project);
        teamDAO.persist(team);
    }

    public Team getTeamById(int teamId) {
        return teamDAO.get(teamId);
    }

    public Team getTeamByName(String name) {
        return teamDAO.getByName(name);
    }

    public List<Team> getTeamsByProject(Project project) {
        return teamDAO.getByProject(project);
    }

    public List<Team> getAllTeams() {
        return teamDAO.getAll();
    }

    public void updateTeam(int teamId, String name, int projectId) {
        Team team = teamDAO.get(teamId);
        team.setTeamName(name);
        team.setProject(projectDAO.get(projectId));
    }

    public void remove(Team team) { // herjasi on kuli removeTeam -> onhan oikein nyt ?
        teamDAO.remove(team);
    }

    public List<Team> loadTeamsByProject(Project project) {
        return teamDAO.getByProject(project);
    }

    public void createTask(String name, TaskState ts, TaskType tt, float hours, String description, ObservableList<Account> accounts, ObservableList<Team> teams, Project project) {
        Task task = new Task(name, ts, tt, hours, description);
        task.setProject(project);

        if (accounts != null) {
           // accounts.forEach(task::setAccounts);
            task.setAccounts((Set<Account>) accounts); // herjasi kun oli assignAccount -> onhan oikein nyt?
        }
        if (teams != null) {
            teams.forEach(task::assignToTeam);
        }
        taskDAO.persist(task);
    }

    public void updateTask(Task task, String name, TaskState ts, TaskType tt, float hours, String description) {
        task.setName(name);
        task.setTaskState(ts);
        task.setTaskType(tt);
        task.setHours(hours);
        task.setDescription(description);
    }

    public Task getTaskById(int taskId) {
        return taskDAO.get(taskId);
    }

    public List<Task> getTaskByProject(Project project) {
        return taskDAO.getByProject(project);
    }

    public List<Task> getTaskByTeam(Team team) {
        return taskDAO.getByTeam(team);
    }

    public List<Task> getTaskByTaskType(TaskType taskType) {
        return taskDAO.getByTaskType(taskType);
    }

    public List<Task> getTaskByAccount(Account account) {
        return taskDAO.getByAccount(account);
    }

    public List<Task> getAllTasks() {
        return taskDAO.getAll();
    }

    public void removeTask(Task task) {
        taskDAO.remove(task);
    }

    public void createTaskType(String name) {
        taskTypeDAO.persist(new TaskType(name));
    }

    public void updateTaskType(TaskType taskType, String name) {
        taskType.setName(name);
        taskTypeDAO.update(taskType);
    }

    public TaskType getTaskTypeByName(String name) {
        return taskTypeDAO.getByName(name);
    }

    public void removeTaskType(TaskType taskType) {
        taskTypeDAO.remove(taskType);
    }

    public List<TaskType> getAllTaskTypes() {
        return taskTypeDAO.getAll();
    }

    public void createEvent(String description, LocalDate date, float hours, Account account) {
        Event event = new Event(description, date, hours, account);
        eventDAO.persist(event);
    }

    public void updateEvent(Event event, String description, LocalDate date, float hours, Account account) {
        event.setDescription(description);
        event.setDate(date);
        event.setHours(hours);
        event.setAccount(account);
        eventDAO.update(event);
    }

    public Event getEventById(int eventId) {
        return eventDAO.get(eventId);
    }

    public List<Event> getEventsByAccount(Account account) {
        return eventDAO.getByAccount(account);
    }

    public List<Event> getAllEvents() {
        return eventDAO.getAll();
    }

    public void removeEvent(Event event) {
        eventDAO.remove(event);
    }

    public void createSprint(String name, LocalDate startDate, LocalDate endDate, Project project) {
        Sprint sprint = new Sprint(name, startDate, endDate, project); // tässä oli pelkkä project
        sprintDAO.persist(sprint);
    }

    public void updateSprint(Sprint sprint, String name, LocalDate startDate, LocalDate endDate) {
        sprint.setName(name);
        sprint.setStartDate(startDate);
        sprint.setEndDate(endDate);
    }

    public Sprint getSprintById(int sprintId) {
        return sprintDAO.get(sprintId);
    }

    public List<Sprint> getAllSprints() {
        return sprintDAO.getAll();
    }

    public void removeSprint(Sprint sprint) {
        sprintDAO.remove(sprint);
    }

    public void updateTask(Task task) {
        taskDAO.update(task);
    }

    //TODO controller implementation needs to be further refactored
    @Override
    public IViewController getActiveViewController() {
        return AppState.getInstance().getViewController();
    }

    @Override
    public void setActiveViewController(IViewController viewController) {
        AppState.getInstance().setViewController(viewController);
    }
}
