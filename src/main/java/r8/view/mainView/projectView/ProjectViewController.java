package r8.view.mainView.projectView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import r8.controller.Controller;
import r8.controller.IControllerAccount;
import r8.controller.IControllerMain;
import r8.model.Project;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateMain;
import r8.view.IViewController;
import r8.view.navigation.GetView;

import java.io.IOException;

/**
 * Controller for {@link Project} specific view
 * @author Aarni Pesonen
 */
public class ProjectViewController {

    @FXML
    private Label labelDescription;
    @FXML
    private Label labelProjectName;
    @FXML
    private BorderPane projectSubViewPane;

    private final IAppStateMain appStateMain = AppState.getInstance();
    private final IControllerMain controller = new Controller();
    private final IControllerAccount controllerAccount = new Controller();
    private final IViewController viewController = controller.getActiveViewController();
    private final AppState appState = AppState.getInstance();
    private final Project project = appState.getSelectedProject();

    private boolean admin;
    private String currentSubview;

    @FXML
    private void initialize() {
        if (controllerAccount.getAccount() != null)
            admin = appStateMain.getIsAdmin();

        handleNavigation("overview-subview");
        setProjectInfo();
    }

    /**
     * Handles subview navigation based on triggering ActionEvents userData
     * @param event event triggering navigation
     */
    @FXML
    public void handleNavigation(ActionEvent event) {
        GetView viewLoader = new GetView();
        final Node eventSource = (Node) event.getSource();
        String userData = (String) eventSource.getUserData();

        if (!userData.equals(currentSubview)) {
            System.out.println("Clicked " + userData);
            projectSubViewPane.setCenter(viewLoader.getView(userData));
            currentSubview = userData;
        }
    }

    /**
     * Handles subview navigation
     * @param viewName string indicates requested .fxml resource name
     */
    @FXML
    public void handleNavigation(String viewName) {

            GetView viewLoader = new GetView();
            projectSubViewPane.setCenter(viewLoader.getView(viewName));
            currentSubview = viewName;
    }

    /**
     * Handles subview navigation based on trigger ActionEvent
     * @param event triggering the navigation
     */
    @FXML
    private void navigate (ActionEvent event) {
        viewController.handleNavigation(event);
    }

    /**
     * Sets {@link Project} to be display in current view
     */
    private void setProjectInfo() {
        labelProjectName.setText(project.getName());
        labelDescription.setText(project.getDescription());
    }

}


