package r8.model.appState;


import r8.model.Project;
import r8.view.mainView.MainViewController;

public interface IAppStateMain {

    MainViewController getMainViewController();

    void setMainViewController(MainViewController mainViewController);

    boolean getIsAdmin();

    void setIsAdmin(boolean isAdmin);

    void createTeam(String name, Project project);
    Project createProject(String name, String description);
}
