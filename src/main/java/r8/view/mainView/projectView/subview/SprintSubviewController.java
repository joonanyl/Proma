package r8.view.mainView.projectView.subview;

import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import r8.controller.Controller;
import r8.controller.IControllerMain;
import r8.model.Account;
import r8.model.Project;
import r8.model.Sprint;

import r8.model.appState.AppState;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SprintSubviewController {

    @FXML
    private Button btnAddSprint;
    @FXML
    private Button btnAddSprintToDB;
    @FXML
    private Button btnRemoveSprint;
    @FXML
    private Button btnRemoveSprintFromList;
    @FXML
    private DatePicker datePickerEndDate;
    @FXML
    private DatePicker datePickerStartDate;
    @FXML
    private ListView<Sprint> listViewProjectSprints = new ListView<>();
    @FXML
    private ListView<Sprint> listViewSprintsToAdd;
    @FXML
    private TextField textFieldSprintName;

    AppState appState = AppState.INSTANCE;
    IControllerMain controller = new Controller();
    Account account = appState.getLoggedAccount();
    private Project project = appState.getSelectedProject();

    private Set<Project> accountProjects = new HashSet<>();
    private Set<Sprint> projectSprints = new HashSet<>();

    @FXML
    private void initialize() {
        initDatePickers();
        getProjectsFromDB();
        getSprintsFromDB();
        initProjectSprintList();
    }

    @FXML
    void addSprintToList() {
        if (textFieldSprintName != null && datePickerStartDate != null && datePickerEndDate != null) {
            Platform.runLater((() -> {
                listViewSprintsToAdd.getItems().add(new Sprint(textFieldSprintName.getText(), datePickerStartDate.getValue(), datePickerEndDate.getValue()));
                textFieldSprintName.clear();
                datePickerStartDate.setValue(null);
                datePickerEndDate.setValue(null);
            }));
        } else {
            System.out.println("Check input field values.");
        }
    }

    @FXML
    void addSprintToDB() {
        Thread thread = new Thread(() -> {
            List<Sprint> sprints = listViewSprintsToAdd.getItems();
            for (Sprint sprint : sprints){
                project.addSprint(sprint);
            }
            controller.getProjectDAO().update(project);

            Platform.runLater(() -> listViewProjectSprints.getItems().addAll(sprints));
        });
        thread.start();
    }

    @FXML
    void removeSprintFromList() {
        int toRemove = listViewSprintsToAdd.getSelectionModel().getSelectedIndex();
        listViewSprintsToAdd.getItems().remove(toRemove);
    }

    @FXML
    void removeSprintFromProject() {

            Sprint toRemove = listViewProjectSprints.getSelectionModel().getSelectedItem();
            int indexToRemove = listViewProjectSprints.getSelectionModel().getSelectedIndex();

            //does not get removed
            //controller.getSprintDAO().remove(toRemove);
            project.removeSprint(toRemove);
            controller.getProjectDAO().update(project);

            Platform.runLater(() -> listViewProjectSprints.getItems().remove(indexToRemove));

        System.out.println(project.getSprints());
    }

    private void getSprintsFromDB() {
        projectSprints.addAll(controller.getSprintDAO().getByProject(project));
    }

    private void getProjectsFromDB() {
        accountProjects.addAll(controller.getProjectDAO().getByAccount(account));
        ArrayList<Project> projects = new ArrayList<>(accountProjects);
        project = projects.get(0);
    }

    private void initDatePickers() {
        datePickerStartDate = new DatePicker(LocalDate.now());
        datePickerEndDate = new DatePicker(LocalDate.now());
    }

    private void initProjectSprintList() {
        listViewProjectSprints.getItems().addAll(projectSprints);
    }

}
