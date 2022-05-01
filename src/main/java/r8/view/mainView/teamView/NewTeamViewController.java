package r8.view.mainView.teamView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import r8.controller.Controller;
import r8.controller.IControllerAccount;
import r8.controller.IControllerMain;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateMain;
import r8.util.UIElementVisibility;
import r8.view.IViewController;
import r8.view.navigation.GetView;

import java.io.IOException;

public class NewTeamViewController {

    @FXML
    private Button btnNewProject;

    @FXML
    private Label btnStatus1;

    @FXML
    private Button buttonPersonnel;

    @FXML
    private Button buttonSprint;

    @FXML
    private Button buttonSprint1;

    @FXML
    private Button buttonSprint11;

    @FXML
    private Button buttonTeam;

    @FXML
    private Button buttonTeam1;

    @FXML
    private Button buttonTeam11;

    @FXML
    private Button buttonTeam111;


    @FXML
    private ImageView imageTaskType;

    @FXML
    private Label labelCreatedBy;

    @FXML
    private Label labelProjectName;

    @FXML
    private Label labelQuickDescription;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private HBox projectNavBar;

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

    public void initialize() throws IOException {
        System.out.println("Using NewProjectViewController!");
        if (controllerAccount.getAccount() != null)
            admin = appStateMain.getIsAdmin();

        handleNavigation("sprint-subview");
        //adminVisibility(admin);
        visibility.toggleAdminVisibility(containerAdmin, admin);
    }

    @FXML
    public void handleNavigation(ActionEvent event) throws IOException {
        GetView viewLoader = new GetView();
        final Node eventSource = (Node) event.getSource();
        String userData = (String) eventSource.getUserData();

        if (!userData.equals(currentSubview)) {
            System.out.println("Clicked " + userData);
            teamsSubViewPane.setCenter(viewLoader.getView(userData));
            currentSubview = userData;
        }
    }

    @FXML
    public void handleNavigation(String viewName) throws IOException {
        GetView viewLoader = new GetView();
        teamsSubViewPane.setCenter(viewLoader.getView(viewName));
        currentSubview = viewName;
    }

    @FXML
    private void navigate (ActionEvent event) throws IOException {
        viewController.handleNavigation(event);
    }

}


