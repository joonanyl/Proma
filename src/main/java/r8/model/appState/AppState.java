package r8.model.appState;

import r8.controller.Controller;
import r8.controller.IControllerMain;
import r8.model.task.Task;
import r8.model.*;
import r8.view.IViewController;
import java.util.List;

/**
 * @author Aarni Pesonen
 */
public enum AppState implements IAppStateMain {

	INSTANCE;
	private Account loggedAccount = null;
	private Project selectedProject = null;
	private Task selectedTask = null;
	private IControllerMain controller = new Controller();
	private List<Project> projectsList;
	private boolean tooltipsEnabled = true;

	// reference to active viewController
	// currently needed when navigating from subviews
	private IViewController viewController;

	AppState() {}

	public static AppState getInstance()  { return INSTANCE; }

	public Account getLoggedAccount() { return loggedAccount; }

	public void setLoggedAccount(Account loggedAccount) {
		this.loggedAccount = loggedAccount;
	}

	public IViewController getViewController() { return viewController; }

	public void setViewController(IViewController viewController) {
		this.viewController = viewController;
	}

	@Override
	public Account getAccount() {
		if(loggedAccount == null)
			return loggedAccount = controller.getAllAccounts().get(0);

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
	public boolean getTooltipsEnabled() {
		return tooltipsEnabled;
	}

	@Override
	public void setTooltipsEnabled(boolean tooltipsEnabled) {
		this.tooltipsEnabled = !tooltipsEnabled;
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

		if (selectedProject == null) {
			List<Project> projects = controller.getProjectDAO().getByAccount(loggedAccount);
			selectedProject = projects.get(0);
		}

		return this.selectedProject;
	}
}
