package r8.view.mainView.projectView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.apache.commons.collections4.Get;
import r8.controller.Controller;
import r8.controller.IControllerAccount;
import r8.controller.IControllerMain;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateMain;
import r8.view.IViewController;
import r8.view.navigation.GetView;

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
    private final IControllerMain controller = new Controller();
    private final IControllerAccount controllerAccount = new Controller();
    private final IViewController viewController = controller.getActiveViewController();
    private final GetView getView = new GetView();

    private boolean admin;
    private String currentSubview;

    @FXML
    private void initialize() {
        if (controllerAccount.getAccount() != null)
            admin = appStateMain.getIsAdmin();

        handleNavigation("overview-subview");
        //adminVisibility(admin);
    }

    @FXML
    public void handleNavigation(ActionEvent event) throws IOException {
        GetView viewLoader = new GetView();
        final Node eventSource = (Node) event.getSource();
        String userData = (String) eventSource.getUserData();

        if (!userData.equals(currentSubview)) {
            System.out.println("Clicked " + userData);
            projectSubViewPane.setCenter(viewLoader.getView(userData));
            currentSubview = userData;
        }
    }

    @FXML
    public void handleNavigation(String viewName) {

            GetView viewLoader = new GetView();
            projectSubViewPane.setCenter(viewLoader.getView(viewName));
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
        viewController.handleNavigation(event);
    }

}


