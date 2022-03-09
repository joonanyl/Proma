package r8.view.projectView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateMain;

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

    final IAppStateMain appStateMain = AppState.getInstance();
    private boolean admin;

    public void initialize() {
        admin = appStateMain.getIsAdmin();
        adminVisibility(admin);
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
