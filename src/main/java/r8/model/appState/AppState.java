package r8.model.appState;

import javafx.collections.ObservableList;
import r8.controller.Controller;
import r8.model.Account;
import r8.model.Project;
import r8.model.Team;
import r8.model.task.Task;
import r8.model.task.TaskState;
import r8.model.task.TaskType;
import r8.view.loginView.LoginViewController;
import r8.view.mainView.MainViewController;

import java.util.List;

public class AppState extends Thread implements IAppStateLogin, IAppStateMain {

	private static volatile AppState INSTANCE = null;
	private Account loggedAccount = null;
	private Project selectedProject = null;

	private LoginViewController loginViewController;
	private MainViewController mainViewController;
	private Controller daoController = new Controller(this);

	private Task selectedTask = daoController.getAllTasks().get(0);

	private List<Project> projects;

	private AppState() {}

	public static AppState getInstance() {
		if(INSTANCE == null) {
			synchronized (AppState.class) {
				if (INSTANCE == null) {
					INSTANCE = new AppState();
				}
			}
		}
		return INSTANCE;
	}

	@Override
	public List<Project> getProjects(){
		this.loadProjects();
		return this.projects;
	}

	public void loadProjects() {
		this.projects = daoController.loadProjects(loggedAccount);

		for (Project p: projects) {
			p.setTeams(daoController.loadTeamsByProject(p));
		}
	}

	public Account getLoggedAccount() {
		return loggedAccount;
	}

	public void setLoggedAccount(Account loggedAccount) {
		this.loggedAccount = loggedAccount;
	}

	@Override
	public void createAccount(String firstName, String lastName, String email, String password) {
		daoController.createAccount(firstName, lastName, email, password);
	}

	@Override
	public void createProject(String name, String description, ObservableList<Account> accounts, ObservableList<String> teams) {
		daoController.createProject(name, description,accounts, teams);
	}

	@Override
	public void createTeam(String name, Project project) {
		daoController.createTeam(name, project);
	}

	@Override
	public boolean authenticateLogin(String email, String password) {
		if(daoController.authenticateLogin(email, password)){
			setLoggedAccount(daoController.getAccountByEmail(email));
			return true;
		}
		return false;
	}

	public LoginViewController getLoginViewController() {
		return loginViewController;
	}

	public MainViewController getMainViewController() {
		return mainViewController;
	}

	public void setLoginViewController(LoginViewController loginViewController) {
		this.loginViewController = loginViewController;
	}

	public void setMainViewController(MainViewController mainViewController) {
		this.mainViewController = mainViewController;
	}

	@Override
	public Account getAccount() {
		return this.loggedAccount;
	}

	public boolean getIsAdmin() {
		if (loggedAccount.getAdmin() != null) {
			return loggedAccount.getAdmin();
		}
		return false;
	}

	@Override
	public List<Account> getAllAccounts(){
		return daoController.getAllAccounts();
	}

	@Override
	public void setIsAdmin(boolean isAdmin) {
		loggedAccount.setAdmin(!isAdmin);
	}


	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	@Override
	public void createTask(String name, TaskState taskState, TaskType taskType, float hours, String desc, ObservableList<Account> accounts, ObservableList<Team> teams){
		daoController.createTask(name,taskState,taskType,hours,desc,accounts,teams);
	}

	@Override
	public void createTaskType(String name){
		daoController.createTaskType(name);
	}

	@Override
	public List<TaskType> getAllTaskTypes(){
		return daoController.getAllTaskTypes();
	}

	@Override
	public List<Team> getAllTeams(){
		return daoController.getAllteams();
	}

	@Override
	public void setSelectedTask(Task task){
		this.selectedTask = task;
	}
	@Override
	public Task getSelectedTask(){
		return this.selectedTask;
	}
	@Override
	public void setSelectedProject(Project project){
		this.selectedProject = project;
	}
	@Override
	public Project getSelectedProject(){
		return this.selectedProject;
	}

	@Override
	public void updateTask(Task task){
		daoController.updateTask(task);
	}
}
