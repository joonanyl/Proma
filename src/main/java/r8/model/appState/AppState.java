package r8.model.appState;

import r8.controller.Controller;
import r8.model.Account;

public class AppState extends Thread implements IAppStateLogin, IAppStateMain {

	private static AppState INSTANCE = null;
	private Account loggedAccount = null;

	private Controller daoController = new Controller();

	private AppState() {}

	public static AppState getInstance() {
		if(INSTANCE == null)
			INSTANCE = new AppState();

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

}
