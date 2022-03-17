package r8.model.appState;

import r8.model.*;
import r8.model.task.Task;

import r8.view.IViewController;

import java.util.List;


public class AppState extends Thread implements IAppStateMain {

	private static volatile AppState INSTANCE = null;
	private Account loggedAccount = null;
	private Project selectedProject = null;

	private List<Project> projectsList;

	private IViewController viewController;

	private Task selectedTask = null;

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

	public Account getLoggedAccount() {
		return loggedAccount;
	}

	public void setLoggedAccount(Account loggedAccount) {
		this.loggedAccount = loggedAccount;
	}

	public IViewController getViewController() { return viewController; }

	public void setViewController(IViewController viewController) {
		this.viewController = viewController;
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

}
