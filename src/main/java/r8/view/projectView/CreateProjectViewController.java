package r8.view.projectView;


import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;
import r8.model.Account;
import r8.model.Project;
import r8.model.Team;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateMain;

import java.io.IOException;
import java.util.*;

public class CreateProjectViewController {

    @FXML
    private TextField textProjectName;

    @FXML
    private TextArea textDescription;

    @FXML
    private TextField textTeamName;

    @FXML
    private ListView<String> listViewTeamsToBeCreated;

    @FXML
    private ListView listViewSprints;

    @FXML
    private TextField textSprintName;

    @FXML
    private SearchableComboBox<Account> accountSearchableComboBox;

    @FXML
    private ListView<Account> listViewAssignedAccounts;


    final IAppStateMain appStateMain = AppState.getInstance();

    public void initialize(){
        Account acc = new Account("a", "b", "email", "pw");
        accountSearchableComboBox.getItems().add(acc);
    }

    @FXML
    private void createTeam(){
        String tn = textTeamName.getText();
        if(!tn.matches("[a-zA-Z0-9]{2,20}")){
            return;
        }
        Platform.runLater(()->{
            listViewTeamsToBeCreated.getItems().add(tn);
            textTeamName.clear();
        });
    }

    @FXML
    private void removeTeam(){
        String selected = listViewTeamsToBeCreated.getSelectionModel().getSelectedItem();
        listViewTeamsToBeCreated.getItems().remove(selected);
    }

    @FXML
    private void AssignUser(){
        Account account = accountSearchableComboBox.getSelectionModel().getSelectedItem();
        if(account != null){
            if(!listViewAssignedAccounts.getItems().contains(account)){
                listViewAssignedAccounts.getItems().add(account);
            }
            accountSearchableComboBox.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void removeAssigned(){
        Account acc = listViewAssignedAccounts.getSelectionModel().getSelectedItem();
        if(acc != null){
            listViewAssignedAccounts.getItems().remove(acc);
        }
    }

    @FXML
    private void createProject(){
        String projectName = textProjectName.getText();
        String projectDesc = textDescription.getText();
        if(!projectName.matches("[a-zA-Z0-9]{3,20}")){
            return;
        }
        if(projectDesc.length() > 255){
            return;
        }
        Project newProject = new Project();
        newProject.setDescription(projectDesc);
        newProject.setName(projectName);

        ObservableList<String> teamsStringList = listViewTeamsToBeCreated.getItems();
        List<Team> teamsList = new ArrayList<>();

        teamsStringList.forEach((item) -> {
            teamsList.add(new Team(item.toString(), newProject));
        });

        newProject.setTeams(teamsList);

        newProject.setAccounts((Set<Account>)listViewAssignedAccounts.getItems());

    }


    @FXML
    private void navigate(ActionEvent event) throws IOException {
        appStateMain.getMainViewController().handleNavigation(event);
    }
}
