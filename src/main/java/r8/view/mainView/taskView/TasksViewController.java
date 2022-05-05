package r8.view.mainView.taskView;

import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.controlsfx.control.SearchableComboBox;
import r8.controller.Controller;
import r8.controller.IControllerAccount;
import r8.controller.IControllerMain;
import r8.model.Account;
import r8.model.Project;
import r8.model.appState.AppState;
import r8.model.task.Task;
import r8.util.UIElementVisibility;
import r8.view.IViewController;


import javax.transaction.Transactional;
import java.util.*;

/**
 * Controller for {@link Task} list view
 * @author Teemu Tallskog
 */
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
    private Button tasksViewTooltip;
    @FXML
    private ListView<Task> listViewMyTasks;
    @FXML
    private HBox projectNavBar;
    @FXML
    private SearchableComboBox<Project> comboBoxProjects;

    private ToggleGroup buttonGroup;
    @FXML
    private ToggleButton btnProject;
    @FXML
    private ToggleButton btnPersonal;
    @FXML
    private ToggleButton btnTeam;
    @FXML
    private ToggleButton btnAll;

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
    @FXML
    private Project selectedProject;
    @FXML
    private Set<Task> allTasks;
    @FXML
    private Set<Task> projectTasks;
    @FXML
    private Set<Task> personalTasks;
    @FXML
    private Set<Task> teamTasks;

    private final UIElementVisibility visibility = new UIElementVisibility();
    private final TasksViewController tasksViewController = this;
    private final IControllerAccount controllerAccount = new Controller();
    private final IControllerMain controller = new Controller();
    private final IViewController viewController = controller.getActiveViewController();

    @FXML
    public void initialize(){
        visibility.setTooltipVisibility(tasksViewTooltip);
        personalTasks = new HashSet<>();
        teamTasks = new HashSet<>();
        projectTasks = new HashSet<>();
        allTasks = new HashSet<>();
        buttonGroup = new ToggleGroup();
        btnProject.setToggleGroup(buttonGroup);
        btnPersonal.setToggleGroup(buttonGroup);
        btnTeam.setToggleGroup(buttonGroup);
        btnAll.setToggleGroup(buttonGroup);
        buttonGroup.selectToggle(btnProject);
        List<Project> projectsList = controller.loadProjects(AppState.getInstance().getAccount());
        System.out.println(projectsList);
        if(projectsList != null){
            comboBoxProjects.getItems().setAll(projectsList);
        }
        addChangeListener();
        listViewChangeListener();
    }

    @FXML
    private void navigate(ActionEvent event) {
        try {
            CustomTaskComponentController selectedObject = taskListView.getSelectionModel().getSelectedItem();
            Task selectedTask = selectedObject.getTask();
            AppState.getInstance().setSelectedTask(selectedTask);
            viewController.handleSubviewNavigation(event);
        } catch (NullPointerException e) {
            System.out.println("No task selected or the selected task is null.");
        }
    }

    @FXML
    void navigateNewTask(ActionEvent event) {
        viewController.handleSubviewNavigation(event);
    }

    private void addChangeListener(){
        comboBoxProjects.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                selectedProject = newValue;
                retrieveTasks();
            }
        });
        buttonGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> updateView());
    }

    private void listViewChangeListener(){
        taskListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                labelProjectInfo.setText(newValue.getTask().getProject().getName());
                labelTaskType.setText(newValue.getTask().getTaskType().toString());
                labelTaskState.setText(newValue.getTask().getTaskStateString());
            }
        });
    }

    /**
     * Retrieves tasks into the tasks view
     */
    @Transactional
    public void retrieveTasks(){
        selectedProject = controller.getProjectById(selectedProject.getProjectId());
        projectTasks = selectedProject.getTasks();
        personalTasks.clear();
        teamTasks.clear();
        Account loggedAccount = AppState.getInstance().getLoggedAccount();
        allTasks.addAll(controller.getTaskDAO().getByAccount(loggedAccount));
        if(projectTasks != null){
            projectTasks.forEach(task -> {
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
        projectTasks.remove(task);
        controller.removeTask(task);
        updateView();
    }

    private void updateView(){
        Platform.runLater(()->{
            Toggle tb = buttonGroup.getSelectedToggle();
            if(tb != null){
                if(tb == btnProject){
                    taskListView.getItems().clear();
                    if(projectTasks != null){
                        projectTasks.forEach(task -> {
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
                if(tb == btnAll){
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