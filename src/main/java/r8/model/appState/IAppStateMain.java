package r8.model.appState;


import r8.model.Account;
import r8.model.Project;
import r8.view.mainView.MainViewController;

public interface IAppStateMain {

    MainViewController getMainViewController();

    void setMainViewController(MainViewController mainViewController);

    boolean getIsAdmin();

    void setIsAdmin(boolean isAdmin);

    Account getAccount();

    void createTeam(String name, Project project);

    Project createProject(String name, String description);
}
