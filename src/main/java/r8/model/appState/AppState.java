package r8.model.appState;

import r8.controller.Controller;
import r8.controller.IControllerMain;
import r8.model.task.Task;
import r8.model.*;
import r8.view.IViewController;
import java.util.List;

/**
 * AppState stores runtime specific data used by the applications functions.
 * This includes current user {@link Account}, active {@link IViewController}
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

	/**
	 * Returns users {@link Account} information
	 * @return account user is logged in to the software with
	 */
	public Account getLoggedAccount() { return loggedAccount; }

	/**
	 * Sets the users account as a logged in account
	 * @param loggedAccount account user is logged in to the software with
	 */
	public void setLoggedAccount(Account loggedAccount) {
		this.loggedAccount = loggedAccount;
	}

	/**
	 * Returns active {@link IViewController}. The view controller reference is used when navigating
	 * to subview of login view or main view
	 * @return active view controller as an interface
	 */
	public IViewController getViewController() { return viewController; }

	/**
	 * Sets the active {@link IViewController}
	 * @param viewController is the controller of the active parent view in which subviews are loaded
	 */
	public void setViewController(IViewController viewController) {
		this.viewController = viewController;
	}

	/**
	 * Used to bypass entering {@link Account} login information
	 * Only available in development builds
	 * @return
	 */
	@Override
	public Account getAccount() {
		if(loggedAccount == null)
			return loggedAccount = controller.getAllAccounts().get(0);

		return this.loggedAccount;
	}

	/**
	 * Returns user {@link Account} admin status
	 * @return account admin status
	 */
	public boolean getIsAdmin() {
		if (loggedAccount.getAdmin() != null) {
			return loggedAccount.getAdmin();
		}
		return false;
	}

	/**
	 * Sets user account admin status
	 * Only available in development builds
	 * @param isAdmin used to indicate admin status on/off
	 */
	@Override
	public void setIsAdmin(boolean isAdmin) {
		loggedAccount.setAdmin(!isAdmin);
	}

	/**
	 * Returns user tooltip preference information
	 * @return user tooltip preference boolean
	 */
	@Override
	public boolean getTooltipsEnabled() {
		return tooltipsEnabled;
	}

	/**
	 * Sets user tooltip preference
	 * @param tooltipsEnabled boolean to indicate users tooltip preferences
	 */
	@Override
	public void setTooltipsEnabled(boolean tooltipsEnabled) {
		this.tooltipsEnabled = !tooltipsEnabled;
	}

	/**
	 * Sets a {@link Task} to be currently active
	 * Relayed as a parameter when user navigates to {@link Task} specific subviews
	 * @param task that is currently active
	 */
	@Override
	public void setSelectedTask(Task task){
		this.selectedTask = task;
	}

	/**
	 * Returns the currently active {@link Task}
	 * when navigating to {@link Task} specific subviews
	 * @return currently active task
	 */
	@Override
	public Task getSelectedTask(){
		return this.selectedTask;
	}

	/**
	 * Sets a {@link Project} to be currently active
	 * Relayed as a parameter when user navigates to {@link Project} specific subviews
	 * @param project that is currently active
	 */
	@Override
	public void setSelectedProject(Project project){
		this.selectedProject = project;
	}

	/**
	 * Returns the currently active {@link Project}
	 * when navigating to {@link Project} specific subviews
	 * @return currently active project
	 */
	@Override
	public Project getSelectedProject(){

		if (selectedProject == null) {
			List<Project> projects = controller.getProjectDAO().getByAccount(loggedAccount);
			selectedProject = projects.get(0);
		}

		return this.selectedProject;
	}
}
