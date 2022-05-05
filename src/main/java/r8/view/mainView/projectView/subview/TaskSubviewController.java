package r8.view.mainView.projectView.subview;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import r8.controller.Controller;
import r8.controller.IControllerMain;
import r8.model.Project;
import r8.model.appState.AppState;
import r8.model.task.Task;
import r8.view.IViewController;

import java.io.IOException;

/**
 * Controller for project task subview
 * @author Aarni Pesonen
 */
public class TaskSubviewController {

    @FXML
    private Label labelDescription;
    @FXML
    private Label labelHoursWorked;
    @FXML
    private Label labelTaskName;
    @FXML
    private Label labelTaskState;
    @FXML
    private Label labelTaskType;
    @FXML
    private ListView<Task> listViewProjectTasks;

    IControllerMain controller = new Controller();
    private final AppState appState = AppState.getInstance();
    private Project project = appState.getSelectedProject();
    private Task selectedTask;

    /**
     * Initializes the subview and retrieves {@link Task} related to active {@link Project}.
     */
    @FXML
    public void initialize() {
        System.out.println("project id " +project.getProjectId());
        Thread thread = new Thread(() -> listViewProjectTasks.getItems().addAll(controller.getTaskDAO().getByProject(project)));
        thread.start();
        listViewListener();
    }

    /**
     * Transfers selected task info to appState and calls navigate()
     * @param event triggering the method call
     */
    @FXML
    private void goToTask(ActionEvent event)  {
        appState.setSelectedTask(selectedTask);
        navigate(event);
    }

    /**
     * Navigates to view determined by triggering event userData
     * @param event triggering the method call
     */
    @FXML
    private void navigate(ActionEvent event) {
        IViewController viewController = controller.getActiveViewController();
        viewController.handleNavigation(event);
    }

    /**
     * Adds listener to project tasks listview
     */
    private void listViewListener() {
        listViewProjectTasks.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                selectedTask = newValue;
                setTaskInfo(newValue);
            }
        });
    }

    /**
     * Sets {@link Task} info based on user input
     * @param task to be updated
     */
    private void setTaskInfo(Task task) {
        labelTaskName.setText(task.getName());
        labelTaskState.setText(task.getTaskStateString());
        labelTaskType.setText(task.getTaskType().toString());
        labelHoursWorked.setText(Float.toString(task.getHours()));
        labelDescription.setText(task.getDescription());
    }
}
