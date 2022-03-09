package r8.view.projectView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import r8.App;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateMain;
import r8.view.navigation.GetView;

import java.io.IOException;

public class ProjectViewController {

    @FXML
    private Button btnNewProject;

    @FXML
    private Label btnStatus;

    @FXML
    private Label btnStatus1;

    @FXML
    private ComboBox<?> comboBoxProjectStatus;

    @FXML
    private ImageView imageTaskType;

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
    private Label labelRandomness;

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

    final IAppStateMain appStateMain = AppState.getInstance();
    private String currentSubview;
    private App app;
    private boolean admin;

    private void initialize() throws IOException {
        admin = appStateMain.getIsAdmin();
        adminVisibility(admin);
        handleNavigation("sprint-subview");
    }

    public void setMainApp(App app) {
        this.app = app;
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
    public void handleNavigation(String viewName) throws IOException {
        GetView viewLoader = new GetView();
        projectSubViewPane.setCenter(viewLoader.getView(viewName));
        currentSubview = viewName;
    }

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        appStateMain.getMainViewController().handleNavigation(event);
    }

    private void adminVisibility(boolean isAdmin) {
        if (!admin) {
            vboxProjectSprints.setVisible(false);
            vboxProjectSprints.setManaged(false);
        }
    }
}
