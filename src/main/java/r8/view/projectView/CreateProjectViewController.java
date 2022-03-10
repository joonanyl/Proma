package r8.view.projectView;


import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;
import r8.model.Account;
import r8.model.Project;
import r8.model.Team;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateMain;
import r8.model.dao.ProjectDAO;
import r8.model.dao.TeamDAO;

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

    @FXML
    private Button btnCreateProject1;
    @FXML
    private Button btnCreateProject;

    private ProjectDAO projectDAO;

    private TeamDAO teamDAO;

    final IAppStateMain appStateMain = AppState.getInstance();

    public void initialize(){
        List<Account> accountList = appStateMain.getAllAccounts();
        if(accountList.contains(appStateMain.getAccount())){
            accountList.remove(appStateMain.getAccount());
        }
        accountList.forEach((account)->{
            accountSearchableComboBox.getItems().add(account);
        });
        projectDAO = new ProjectDAO();
        teamDAO = new TeamDAO();

        // kummallekin create project -napille eventhandlerit
        btnCreateProject1.setOnAction(event -> createProject());
        btnCreateProject.setOnAction(event -> createProject());
    }

    @FXML
    private void createTeam(){
        System.out.println("create team");
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
        System.out.println("CREATEPROJECT");

        String projectName = textProjectName.getText();
        String projectDesc = textDescription.getText();
        if(!projectName.matches("[a-zA-Z0-9\\s ]{3,20}")){
            return;
        }
        if(projectDesc.length() > 255){
            return;
        }
        Project newProject = new Project();
        newProject.setDescription(projectDesc);
        newProject.setName(projectName);

        appStateMain.createProject(projectName, projectDesc, listViewAssignedAccounts.getItems(), listViewTeamsToBeCreated.getItems());

        System.out.println("Uusi projekti luotu ja sent to DB tiimeineen");
    }

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        appStateMain.getMainViewController().handleNavigation(event);
    }
}
