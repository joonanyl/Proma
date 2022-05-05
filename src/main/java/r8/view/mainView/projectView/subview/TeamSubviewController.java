package r8.view.mainView.projectView.subview;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import r8.controller.Controller;
import r8.controller.IControllerMain;
import r8.model.Account;
import r8.model.Project;
import r8.model.Team;
import r8.model.appState.AppState;
import r8.util.UIElementVisibility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Controller for project team subview
 * @author Aarni Pesonen
 */
public class TeamSubviewController {

    @FXML
    private ListView<Team> listViewProjectTeams;
    @FXML
    private ListView<String> listViewTeamsToAdd;
    @FXML
    private TextField textFieldTeamName;
    @FXML
    private Button btnDeleteTeam;
    @FXML
    private VBox vBoxAddTeams;

    private final AppState appState = AppState.INSTANCE;
    private final Account account = appState.getLoggedAccount();
    private Project project;
    private final IControllerMain controller = new Controller();
    private final UIElementVisibility visibility = new UIElementVisibility();
    private Set<Account> allAccounts = new HashSet<>();
    private Set<Team> projectTeams = new HashSet<>();
    private Set<Project> accountProjects = new HashSet<>();

    /**
     * Initializes view. Retrieves account projects from db.
     */
    @FXML
    public void initialize() {
        visibility.toggleAdminVisibility(vBoxAddTeams, account.getAdmin());
        visibility.toggleAdminVisibility(btnDeleteTeam, account.getAdmin());
        project = appState.getSelectedProject();
        System.out.println(project);
        getProjectsTeamsDB();
    }

    /**
     * Adds a team to teamListView
     */
    @FXML
    void addTeamToList() {
       listViewTeamsToAdd.getItems().add(textFieldTeamName.getText());
        textFieldTeamName.clear();
    }

    /**
     * Creates new {@link Team} objects based on listViewTeamsToAdd content
     * and adds these to database.
     */
    @FXML
    void addTeamsToDB() {
        List<Team> teamsToAdd = new ArrayList<>();
        Thread thread = new Thread(() -> {
            List<String> teams = listViewTeamsToAdd.getItems();
            for (String string : teams){
                Team teamToAdd = new Team(string, project);
                teamsToAdd.add(teamToAdd);
                project.addTeam(teamToAdd);
            }

            controller.getProjectDAO().update(project);

            Platform.runLater(() -> {
                listViewTeamsToAdd.getItems().clear();
                listViewProjectTeams.getItems().addAll(teamsToAdd);
                System.out.println("Project updated with new teams");
            });
        });
        thread.start();
    }

    /**
     * Removes an existing {@link Team} from database
     */
    @FXML
    void deleteTeamFromDB() {
        Platform.runLater(() -> {
            Team team = listViewProjectTeams.getSelectionModel().getSelectedItem();
            listViewProjectTeams.getItems().remove(team);
            controller.getTeamDAO().remove(team);
        });
    }

    /**
     * Removes a {@link Team} from listViewTeamsToAdd
      */
    @FXML
    void removeTeamFromList() {
        int toRemove = listViewTeamsToAdd.getSelectionModel().getSelectedIndex();
        listViewTeamsToAdd.getItems().remove(toRemove);
    }

    /**
     * Retrieves user projects from database
     */
    private void getProjectsTeamsDB() {

        Thread thread = new Thread(() -> {

            projectTeams.addAll(controller.getTeamDAO().getByProject(project));

            Platform.runLater(() -> listViewProjectTeams.getItems().addAll(projectTeams));
        });
        thread.start();
    }

}


