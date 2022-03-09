package r8.model.appState;

import r8.controller.Controller;
import r8.model.Account;
import r8.model.Project;
import r8.view.loginView.LoginViewController;
import r8.view.mainView.MainViewController;

import java.util.List;

public class AppState extends Thread implements IAppStateLogin, IAppStateMain {

	private static volatile AppState INSTANCE = null;
	private Account loggedAccount = null;

	private LoginViewController loginViewController;
	private MainViewController mainViewController;
	private Controller daoController = new Controller(this);

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

	public void createProject(String name, String description) {
		daoController.createProject(name, description);
	}

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
	public void setIsAdmin(boolean isAdmin) {
		loggedAccount.setAdmin(!isAdmin);
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
}
