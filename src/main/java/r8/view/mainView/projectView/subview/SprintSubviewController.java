package r8.view.mainView.projectView.subview;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import r8.controller.Controller;
import r8.controller.IControllerMain;
import r8.model.Account;
import r8.model.Project;
import r8.model.Sprint;
import r8.model.Team;
import r8.model.appState.AppState;

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
    private ListView<Sprint> listViewProjectSprints = new ListView<>();
    @FXML
    private ListView<String> listViewSprintsToAdd;
    @FXML
    private TextField textFieldSprintName;

    Account account = AppState.getInstance().getAccount();
    IControllerMain controller = new Controller();

    private Set<Project> accountProjects = new HashSet<>();
    private Set<Sprint> projectSprints = new HashSet<>();

    private Project project;

    @FXML
    private void initialize() {
        getProjectsFromDB();
        getSprintsFromDB();
        initProjectSprintList();
    }

    //TODO Add start and end date
    @FXML
    void addSprintToDB() {
        Thread thread = new Thread(() -> {
            /*List<String> sprints = listViewSprintsToAdd.getItems();
            for (String string : sprints){
                Sprint sprintToAdd = new Sprint(string, project);
                project.addTeam(sprintToAdd);
            }*/

            controller.getProjectDAO().update(project);

            Platform.runLater(() -> System.out.println("Project updated with new sprints"));
        });
        thread.start();
    }

    @FXML
    void addSprintToList() {
        listViewSprintsToAdd.getItems().add(textFieldSprintName.getText());
        textFieldSprintName.clear();
    }

    @FXML
    void removeSprintFromList() {
        int toRemove = listViewSprintsToAdd.getSelectionModel().getSelectedIndex();
        listViewSprintsToAdd.getItems().remove(toRemove);
    }

    @FXML
    void removeSprintFromProject() {

    }

    private void getSprintsFromDB() {
        projectSprints.addAll(controller.getSprintDAO().getByProject(project));
    }

    private void getProjectsFromDB() {
        accountProjects.addAll(controller.getProjectDAO().getByAccount(account));
        ArrayList<Project> projects = new ArrayList<>(accountProjects);
        project = projects.get(0);
    }

    private void initProjectSprintList() {
        listViewProjectSprints.getItems().addAll(projectSprints);
    }

}
