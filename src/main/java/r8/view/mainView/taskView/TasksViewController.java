package r8.view.mainView.taskView;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import r8.model.appState.IAppStateMain;
import r8.model.task.Task;
import r8.view.IViewController;

import javax.swing.*;
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


    private final IControllerAccount controllerAccount = new Controller();
    private final IControllerMain controller = new Controller();
    private final IViewController viewController = controller.getActiveViewController();

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        Task selectedTask = listViewMyTasks.getSelectionModel().getSelectedItem();
        if(selectedTask == null){
            return;
        }
        AppState.getInstance().setSelectedTask(selectedTask);
        viewController.handleNavigation(event);
    }

    @FXML
    private void navigateNewTask(ActionEvent event) throws IOException {
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
        buttonGroup = new ToggleGroup();
        btnOverview.setToggleGroup(buttonGroup);
        btnPersonal.setToggleGroup(buttonGroup);
        btnTeam.setToggleGroup(buttonGroup);
        buttonGroup.selectToggle(btnOverview);
        List<Project> projectsList = controller.getProjects();
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
                    selectedProject = controller.getProjectById(newValue.getProjectId());
                    personalTasks.clear();
                    teamTasks.clear();

                    if (selectedProject.getTasks() != null) {
                        allTasks = selectedProject.getTasks();
                        System.out.println(controllerAccount.getAccount().toString());
                        Platform.runLater(() -> {
                            allTasks.forEach((item) -> {
                                item.getAccounts().forEach((account) ->{
                                    if(account.getAccountId() == controllerAccount.getAccount().getAccountId()){
                                        personalTasks.add(item);
                                    }
                                });
                                //TODO add teams
                            });
                            updateView();
                        });
                    }
                }
            }
        });
        buttonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(buttonGroup.getSelectedToggle() != null){
                    //Platform.runLater(()->{
                        if(buttonGroup.getSelectedToggle() == btnOverview){
                            listViewMyTasks.getItems().clear();
                            if(allTasks != null){
                                listViewMyTasks.getItems().setAll(allTasks);
                            }
                        }
                        if(buttonGroup.getSelectedToggle() == btnPersonal){
                            listViewMyTasks.getItems().clear();
                            if(personalTasks != null){
                                listViewMyTasks.getItems().setAll(personalTasks);
                            }
                        }
                        if(buttonGroup.getSelectedToggle() == btnTeam){
                            listViewMyTasks.getItems().clear();
                            if(teamTasks != null){
                                listViewMyTasks.getItems().setAll(teamTasks);
                            }
                        }
                    //});
                }
            }
        });
    }

    private void listViewChangeListener(){
        listViewMyTasks.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
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

    private void updateView(){
        Platform.runLater(()->{
            Toggle tb = buttonGroup.getSelectedToggle();
            if(tb != null){
                if(tb == btnOverview){
                    listViewMyTasks.getItems().clear();
                    if(allTasks != null){
                        listViewMyTasks.getItems().setAll(allTasks);
                    }
                }
                if(tb == btnPersonal){
                    listViewMyTasks.getItems().clear();
                    if(personalTasks != null){
                        listViewMyTasks.getItems().setAll(personalTasks);
                    }
                }
                if(tb == btnTeam){
                    listViewMyTasks.getItems().clear();
                    if(teamTasks != null){
                        listViewMyTasks.getItems().setAll(teamTasks);
                    }
                }
            }
        });
    }
}
