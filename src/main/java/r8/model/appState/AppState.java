package r8.model.appState;

import r8.controller.Controller;
import r8.model.Account;
import r8.view.loginView.LoginViewController;
import r8.view.mainView.MainViewController;

public class AppState extends Thread implements IAppStateLogin, IAppStateMain {

	private static volatile AppState INSTANCE = null;
	private Account loggedAccount = null;

	private LoginViewController loginViewController;
	private MainViewController mainViewController;
	private Controller daoController = new Controller(this);

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

	@Override
	public void createAccount(String firstName, String lastName, String email, String password) {
		daoController.createAccount(firstName, lastName, email, password);
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

	public boolean getIsAdmin() {
		return loggedAccount.getAdmin();
	}

	@Override
	public void setIsAdmin(boolean isAdmin) {
		loggedAccount.setAdmin(!isAdmin);
	}
}
