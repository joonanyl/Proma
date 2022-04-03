package r8.view.mainView.taskView;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.SearchableComboBox;
import r8.controller.Controller;
import r8.controller.IControllerAccount;
import r8.controller.IControllerMain;
import r8.model.Account;
import r8.model.Project;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateMain;
import r8.model.task.Task;
import r8.view.IViewController;

import javax.swing.*;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class TasksViewController {

    @FXML
    private Button btnActiveTasks;

    @FXML
    private Button btnCompletedTasks;

    @FXML
    private Button btnGotToTaskView;

    @FXML
    private Button buttonAllTasks;

    @FXML
    private HBox projectNavBar;

    @FXML
    private SearchableComboBox<Project> comboBoxProjects;

    private ToggleGroup buttonGroup;

    @FXML
    private ToggleButton btnOverview;

    @FXML
    private ToggleButton btnPersonal;

    @FXML
    private ToggleButton btnTeam;

    @FXML
    private Label labelProjectInfo;
    @FXML
    private Label labelSprintInfo;
    @FXML
    private Label labelCreatedBy;
    @FXML
    private Label labelPriority;
    @FXML
    private Label labelTaskType;
    @FXML
    private Label labelTaskState;
    @FXML
    private ListView taskListView;

    private TasksViewController tasksViewController = this;
    private final IControllerAccount controllerAccount = new Controller();
    private final IControllerMain controller = new Controller();
    private final IViewController viewController = controller.getActiveViewController();

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        CustomTaskComponentController selectedObject = (CustomTaskComponentController) taskListView.getSelectionModel().getSelectedItem();
        Task selectedTask = selectedObject.getTask();
        if(selectedTask == null){
            return;
        }
        AppState.getInstance().setSelectedTask(selectedTask);
        viewController.handleNavigation(event);
    }

    @FXML
    void navigateNewTask(ActionEvent event) throws IOException {
        viewController.handleNavigation(event);
    }

    @FXML
    private Project selectedProject;
    @FXML
    private List<Task> allTasks;
    @FXML
    private List<Task> personalTasks;
    @FXML
    private List<Task> teamTasks;

    @FXML
    public void initialize(){
        personalTasks = new ArrayList<>();
        teamTasks = new ArrayList<>();
        allTasks = new ArrayList<>();
        buttonGroup = new ToggleGroup();
        btnOverview.setToggleGroup(buttonGroup);
        btnPersonal.setToggleGroup(buttonGroup);
        btnTeam.setToggleGroup(buttonGroup);
        buttonGroup.selectToggle(btnOverview);
        List<Project> projectsList = controller.loadProjects(AppState.getInstance().getAccount());
        System.out.println(projectsList);
        if(projectsList != null){
            comboBoxProjects.getItems().setAll(projectsList);
        }
        addChangeListener();
        listViewChangeListener();
    }

    private void addChangeListener(){
        comboBoxProjects.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Project>() {
            @Override
            public void changed(ObservableValue<? extends Project> observable, Project oldValue, Project newValue) {
                if(newValue != null) {
                    selectedProject = newValue;
                    retrieveTasks();
                }
            }
        });
        buttonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                updateView();
            }
        });
    }

    private void listViewChangeListener(){
        taskListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
            @Override
            public void changed(ObservableValue<? extends Task> observable, Task oldValue, Task newValue) {
                if(newValue != null){
                    labelProjectInfo.setText(newValue.getProject().getName());
                    labelTaskType.setText(newValue.getTaskType().toString());
                    labelTaskState.setText(newValue.getTaskStateString());
                }
            }
        });
    }

    @Transactional
    public void retrieveTasks(){
        selectedProject = controller.getProjectById(selectedProject.getProjectId());
        allTasks = selectedProject.getTasks();
        personalTasks.clear();
        teamTasks.clear();
        Account loggedAccount = AppState.getInstance().getLoggedAccount();
        if(allTasks != null){
            allTasks.forEach(task -> {
                if(task.getAccounts().contains(loggedAccount)){
                    personalTasks.add(task);
                }
                task.getTeams().forEach(team -> {
                    if(team.getAccounts().contains(loggedAccount)){
                        teamTasks.add(task);
                    }
                });
            });
        }
        updateView();
    }

    public void deleteTask(Task task){
        allTasks.remove(task);
        //controller.deleteTask(task);
        updateView();
    }

    private void updateView(){
        Platform.runLater(()->{
            Toggle tb = buttonGroup.getSelectedToggle();
            if(tb != null){
                if(tb == btnOverview){
                    taskListView.getItems().clear();
                    if(allTasks != null){
                        allTasks.forEach(task -> {
                            taskListView.getItems().add(new CustomTaskComponentController(task, this));
                        });
                    }
                }
                if(tb == btnPersonal){
                    taskListView.getItems().clear();
                    if(personalTasks != null){
                        personalTasks.forEach(task -> {
                            taskListView.getItems().add(new CustomTaskComponentController(task, tasksViewController));
                        });
                    }
                }
                if(tb == btnTeam){
                    taskListView.getItems().clear();
                    if(teamTasks != null){
                        teamTasks.forEach(task -> {
                            taskListView.getItems().add(new CustomTaskComponentController(task, tasksViewController));
                        });
                    }
                }
            }
        });
    }
}
