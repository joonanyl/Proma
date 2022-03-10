package r8.view.taskView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import org.controlsfx.control.SearchableComboBox;
import r8.model.Project;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateMain;
import r8.model.task.Task;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class TasksViewController {

    final IAppStateMain appStateMain = AppState.getInstance();

    @FXML
    private Button btnActiveTasks;

    @FXML
    private Button btnCompletedTasks;

    @FXML
    private Button btnGotToTaskView;

    @FXML
    private Button buttonAllTasks;

    @FXML
    private ListView<Task> listViewMyTasks;

    @FXML
    private HBox projectNavBar;

    @FXML
    private SearchableComboBox<Project> comboBoxProjects;

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        appStateMain.getMainViewController().handleNavigation(event);
    }

    public void initialize(){
        List<Project> projectsList = appStateMain.getProjects();
        if(projectsList != null){
            comboBoxProjects.getItems().setAll(projectsList);
        }

    }
}
