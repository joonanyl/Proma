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
import java.util.*;

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
    private ListView<Task> listViewMyTasks;

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
    private ListView<CustomTaskComponentController> taskListView;

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
        viewController.handleSubviewNavigation(event);
    }

    @FXML
    void navigateNewTask(ActionEvent event) throws IOException {
        viewController.handleSubViewNavigation(event);
    }

    @FXML
    private Project selectedProject;
    @FXML
    private Set<Task> allTasks;
    @FXML
    private Set<Task> personalTasks;
    @FXML
    private Set<Task> teamTasks;

    @FXML
    public void initialize(){
        personalTasks = new HashSet<>();
        teamTasks = new HashSet<>();
        allTasks = new HashSet<>();
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
        taskListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomTaskComponentController>() {
            @Override
            public void changed(ObservableValue<? extends CustomTaskComponentController> observable, CustomTaskComponentController oldValue, CustomTaskComponentController newValue) {
                if(newValue != null){
                    labelProjectInfo.setText(newValue.getTask().getProject().getName());
                    labelTaskType.setText(newValue.getTask().getTaskType().toString());
                    labelTaskState.setText(newValue.getTask().getTaskStateString());
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
