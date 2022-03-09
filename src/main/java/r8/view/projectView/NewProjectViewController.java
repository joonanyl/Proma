package r8.view.projectView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import r8.model.Sprint;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateMain;
import r8.model.task.Task;
import r8.model.task.TaskState;
import r8.model.task.TaskType;
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

    final IAppStateMain appStateMain = AppState.getInstance();
    private boolean admin;
    private String currentSubview;

    public void initialize() throws IOException {
        if (appStateMain.getAccount() != null)
            admin = appStateMain.getIsAdmin();

        handleNavigation("sprint-subview");
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
    public void handleNavigation(String viewName) throws IOException {
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
        appStateMain.getMainViewController().handleNavigation(event);
    }

}


