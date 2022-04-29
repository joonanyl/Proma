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
import r8.model.Team;
import r8.model.appState.AppState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TeamSubviewController {

    @FXML
    private Button btnAddTeam;
    @FXML
    private Button btnRemoveTeam;
    @FXML
    private Button buttonCreateTeams;
    @FXML
    private ListView<Team> listViewProjectTeams;
    @FXML
    private ListView<String> listViewTeamsToAdd;
    @FXML
    private TextField textFieldTeamName;

    private final Account account = AppState.getInstance().getAccount();
    private Set<Account> allAccounts = new HashSet<>();
    private Set<Team> projectTeams = new HashSet<>();
    private Set<Project> accountProjects = new HashSet<>();

    IControllerMain controller = new Controller();
    private Project project;

    @FXML
    private void initialize() {
        getProjectsFromDB();
    }

    @FXML
    void addTeamToList() {
       listViewTeamsToAdd.getItems().add(textFieldTeamName.getText());
        textFieldTeamName.clear();
    }

    @FXML
    void addTeamsToDB() {
        Thread thread = new Thread(() -> {
            List<String> teams = listViewTeamsToAdd.getItems();
            for (String string : teams){
                Team teamToAdd = new Team(string, project);
                project.addTeam(teamToAdd);
            }

            controller.getProjectDAO().update(project);

            Platform.runLater(() -> System.out.println("Project updated with new teams"));
        });
        thread.start();
    }

    @FXML
    void removeTeamFromList() {
        int toRemove = listViewTeamsToAdd.getSelectionModel().getSelectedIndex();
        listViewTeamsToAdd.getItems().remove(toRemove);
    }

    private void getProjectsFromDB() {
        accountProjects.addAll(controller.getProjectDAO().getByAccount(account));
        ArrayList<Project> projects = new ArrayList<>(accountProjects);
        project = projects.get(0);
    }

}


