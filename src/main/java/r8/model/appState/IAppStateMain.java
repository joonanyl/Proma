package r8.model.appState;


import r8.model.Account;
import r8.view.mainView.MainViewController;

public interface IAppStateMain {

    MainViewController getMainViewController();

    void setMainViewController(MainViewController mainViewController);

    Account getAccount();

    boolean getIsAdmin();

    void setIsAdmin(boolean isAdmin);
}
