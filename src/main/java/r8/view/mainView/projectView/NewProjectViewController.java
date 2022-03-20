package r8.view.mainView.projectView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import r8.App;
import r8.controller.Controller;
import r8.controller.IControllerAccount;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateMain;

import java.io.IOException;

public class NewProjectViewController {

    @FXML
    private Button btnNewProject;

    @FXML
    private Label btnStatus;

    @FXML
    private ComboBox<?> comboBoxProjectStatus;

    @FXML
    private Label labelCreatedBy;

    @FXML
    private Label labelDescription;

    @FXML
    private Label labelImplementationInfo;

    @FXML
    private Label labelProjectName;

    @FXML
    private Label labelQuickDescription;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private VBox vboxProjectPersonnel;

    @FXML
    private VBox vboxProjectSprints;

    @FXML
    private VBox vboxProjectTasks;

    @FXML
    private BorderPane projectSubViewPane;

    private final IAppStateMain appStateMain = AppState.getInstance();
    //private final IControllerMain controller = new Controller();
    private final IControllerAccount controllerAccount = new Controller();
    //private final IViewController viewController = controller.getActiveViewController();

    private boolean admin;
    private String currentSubview;

    public void initialize() throws IOException {
        System.out.println("Using NewProjectViewController!");
        if (controllerAccount.getAccount() != null)
            admin = appStateMain.getIsAdmin();

        handleNavigation("sprint-subview");
        //adminVisibility(admin);
    }

    @FXML
    private void handleNavigation(ActionEvent event) throws IOException {
        //GetView viewLoader = new GetView();
        final Node eventSource = (Node) event.getSource();
        String userData = (String) eventSource.getUserData();

        if (!userData.equals(currentSubview)) {
            System.out.println("Clicked " + userData);
            projectSubViewPane.setCenter(App.getView(userData));
            currentSubview = userData;
        }
    }

    @FXML
    private void handleNavigation(String viewName) throws IOException {
        //GetView viewLoader = new GetView();
        projectSubViewPane.setCenter(App.getView(viewName));
        currentSubview = viewName;
    }

    /*private void adminVisibility(boolean isAdmin) {
        if (!admin) {
            vboxProjectSprints.setVisible(false);
            vboxProjectSprints.setManaged(false);
        }
    }*/

    @FXML
    private void navigate (ActionEvent event) throws IOException {
        App.handleNavigation(event);
    }

}


