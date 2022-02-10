package r8.view.navigation;

import r8.view.loginView.LoginViewController;
import r8.view.mainView.MainViewController;

public class GlobalControllerRef {

    private static GlobalControllerRef globalControllerRef = null;
    private LoginViewController loginViewController = null;
    private MainViewController mainViewController = null;

    private GlobalControllerRef() {}

    public static GlobalControllerRef getInstance() {
        if (globalControllerRef == null) {globalControllerRef = new GlobalControllerRef();}
        return globalControllerRef;
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
}
