package r8.view.navigation;

import r8.view.loginView.LoginViewController;
import r8.view.mainView.MainViewController;

public class GlobalControllerReference {

    private static GlobalControllerReference globalControllerReference = null;
    private LoginViewController loginViewController = null;
    private MainViewController mainViewController = null;

    private GlobalControllerReference() {}

    public static GlobalControllerReference getInstance() {
        if (globalControllerReference == null) {
            globalControllerReference = new GlobalControllerReference();}
        return globalControllerReference;
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
