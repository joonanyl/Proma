package r8.model.appState;

import r8.view.loginView.LoginViewController;

public interface IAppStateLogin {

    void createAccount(String firstName, String lastName, String email, String password);
    boolean authenticateLogin(String email, String password);

    LoginViewController getLoginViewController();

    void setLoginViewController(LoginViewController loginViewController);
}
