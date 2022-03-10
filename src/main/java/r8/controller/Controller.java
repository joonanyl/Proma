package r8.controller;

import javafx.collections.ObservableList;
import r8.model.*;
import r8.model.appState.AppState;
import r8.model.dao.*;
import r8.model.task.Task;
import r8.model.task.TaskState;
import r8.model.task.TaskType;

import java.time.LocalDate;
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
        return AuthService.authenticatePassword(email, password);
    }

    public void logout() {
        appState.setLoggedAccount(null);
    }

    public void createAccount(String firstName, String lastName, String email, String password) {
        Account account = new Account(firstName, lastName, email, password);
        accountDAO.persist(account);
    }

    public Account getAccount(Integer accountId) {
        return accountDAO.get(accountId);
    }

    public Account getAccountByEmail(String email) {
        return accountDAO.getByEmail(email);
    }

    public List<Account> getAllAccounts(){
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
        if(accountList != null){
            accountList.forEach((item)->{
                project.addAccount(item);
            });
        }
        project.addAccount(accountDAO.get(AppState.getInstance().getLoggedAccount().getAccountId()));
        // Tässä vaiko näkymän latauksessa päivitetään AppStateen projektit?
        projectDAO.persist(project);
        if(teamList != null){
            teamList.forEach((item)->{
                createTeam(item, project);
            });
        }
    }

    public Project getProjectById(int projectId) {
        return projectDAO.get(projectId);
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
        projectDAO.removeProject(project);
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

    public List<Team> getAllteams() {
        return teamDAO.getAll();
    }

    public void updateTeam(int teamId, String name, int projectId) {
        Team team = teamDAO.get(teamId);
        team.setTeamName(name);
        team.setProject(projectDAO.get(projectId));
    }

    public void removeTeam(Team team) {
        teamDAO.removeTeam(team);
    }

    public List<Team> loadTeamsByProject(Project project) {
        return teamDAO.getByProject(project);
    }

    public void createTask(String name, TaskState ts, TaskType tt, float hours, String description, ObservableList<Account> accounts, ObservableList<Team> teams) {
        Task task = new Task(name, ts, tt, hours, description);
        if(accounts != null){
            accounts.forEach((account) ->{
                task.assignAccount(account);
            });
        }
        if(teams != null){
            teams.forEach((team)->{
                task.assignToTeam(team);
            });
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

    public List<Task> getAllTasks() {
        return taskDAO.getAll();
    }

    public void removeTask(Task task) {
        taskDAO.remove(task);
    }

    public void createTaskType(String name){
        taskTypeDAO.persist(new TaskType(name));
    }

    public List<TaskType> getAllTaskTypes(){
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

    public List<Event> getAllEvents() {
        return eventDAO.getAll();
    }

    public void removeEvent(Event event) {
        eventDAO.remove(event);
    }



}
