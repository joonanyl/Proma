package r8.view.mainView.teamView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import r8.controller.Controller;
import r8.controller.IControllerAccount;
import r8.controller.IControllerMain;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateMain;
import r8.util.UIElementVisibility;
import r8.view.IViewController;
import r8.view.navigation.GetView;
import r8.model.Team;



/**
 * Controller for {@link Team} specific subview
 */
public class TeamViewController {

    @FXML
    private VBox containerAdmin = new VBox();

    @FXML
    private BorderPane teamsSubViewPane = new BorderPane();

    private final IAppStateMain appStateMain = AppState.getInstance();
    private final IControllerMain controller = new Controller();
    private final IControllerAccount controllerAccount = new Controller();
    private final IViewController viewController = controller.getActiveViewController();

    private UIElementVisibility visibility = new UIElementVisibility();
    private boolean admin;
    private String currentSubview;

    /**
     * Initializes the view
     */
    public void initialize() {
        System.out.println("Using NewProjectViewController!");
        if (controllerAccount.getAccount() != null)
            admin = appStateMain.getIsAdmin();

        handleNavigation("sprint-subview");
        //adminVisibility(admin);
        visibility.toggleAdminVisibility(containerAdmin, admin);
    }

    /**
     * Handles subview navigation based triggering Action Event userData value
     * @param event triggerin the navigation
     */
    @FXML
    public void handleNavigation(ActionEvent event) {
        GetView viewLoader = new GetView();
        final Node eventSource = (Node) event.getSource();
        String userData = (String) eventSource.getUserData();

        if (!userData.equals(currentSubview)) {
            System.out.println("Clicked " + userData);
            teamsSubViewPane.setCenter(viewLoader.getView(userData));
            currentSubview = userData;
        }
    }

    /**
     * Handles navigation based on string received as an input
     * string should correspond to requested .fxml resource filename
     * @param viewName of the requested .fxml resource
     */
    @FXML
    public void handleNavigation(String viewName) {
        GetView viewLoader = new GetView();
        teamsSubViewPane.setCenter(viewLoader.getView(viewName));
        currentSubview = viewName;
    }

    /**
     * Handles navigation away from {@link Team} specific subview
     * by relaying navigation request to parent {@link IViewController}
     * @param event
     */
    @FXML
    private void navigate (ActionEvent event) {
        viewController.handleNavigation(event);
    }

}


