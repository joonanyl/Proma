package r8.view.mainView.projectView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import r8.controller.Controller;
import r8.controller.IControllerAccount;
import r8.controller.IControllerMain;
import r8.model.Sprint;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateMain;
import r8.model.task.Task;
import r8.model.task.TaskState;
import r8.model.task.TaskType;
import r8.view.IViewController;
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
    ListView listViewTask;

    @FXML
    ListView listViewSprint;

    @FXML
    TextArea textAreaTask;

    @FXML
    TextArea textAreaSprint;

    @FXML
    private BorderPane projectSubViewPane;

    private final IControllerMain controller = new Controller();
    private final IViewController viewController = controller.getActiveViewController();

    private String currentSubview;

    public void initialize() throws IOException {
        Task task = new Task("name", TaskState.NOT_STARTED, new TaskType("type"), 0, "desc");
        listViewTask.getItems().add(task);
        textAreaTask.setEditable(false);
        textAreaSprint.setEditable(false);
        setListeners();
        handleNavigation("sprint-subview");
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

        private void setListeners() {
            listViewTask.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
                @Override
                public void changed(ObservableValue<? extends Task> observable, Task oldValue, Task newValue) {
                    selectTask(newValue);
                }
            });
            listViewSprint.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Sprint>() {
                @Override
                public void changed(ObservableValue<? extends Sprint> observable, Sprint oldValue, Sprint newValue) {
                    selectSprint(newValue);
                }
            });
        }

        private void selectTask(Task task){
            textAreaTask.setText(task.getDescription());
        }

        private void selectSprint(Sprint sprint){
            //sprintTextField.setText();
        }

        @FXML
        private void navigate (ActionEvent event) throws IOException {
            viewController.handleNavigation(event);
        }

    }

