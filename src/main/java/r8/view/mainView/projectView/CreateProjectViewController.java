package r8.view.mainView.projectView;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.control.SearchableComboBox;
import r8.controller.Controller;
import r8.controller.IControllerAccount;
import r8.controller.IControllerMain;
import r8.model.Account;
import r8.model.Project;
import r8.model.Sprint;
import r8.model.Team;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateMain;
import r8.view.IViewController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class CreateProjectViewController {

    @FXML
    private TextField textProjectName;

    @FXML
    private TextArea textDescription;

    @FXML
    private TextField textTeamName;

    @FXML
    private TextField textSprintName;

    @FXML
    private ListView<String> listViewTeamsToBeCreated;

    @FXML
    private ListView<Sprint> listViewSprintsToBeCreated;

    @FXML
    private SearchableComboBox<Account> accountSearchableComboBox;

    @FXML
    private ListView<Account> listViewAssignedAccounts;

    @FXML
    private DatePicker datePickerStartDate;

    @FXML
    private DatePicker datePickerEndDate;

    @FXML
    private Button btnCreateProject1;

    @FXML
    private Button btnCreateProject;

    final IAppStateMain appStateMain = AppState.getInstance();
    final IControllerAccount controllerAccount = new Controller();
    final IControllerMain controller = new Controller();

    public void initialize() {
        List<Account> accountList = controller.getAllAccounts();
        accountList.remove(AppState.getInstance().getLoggedAccount());
        accountList.forEach((account)-> accountSearchableComboBox.getItems().add(account));

        // kummallekin create project -napille eventhandlerit
        btnCreateProject1.setOnAction(event -> createProject());
        btnCreateProject.setOnAction(event -> createProject());

        initDatePickers();
    }

    @FXML
    private void createTeam() {
        System.out.println("create team");
        String tn = textTeamName.getText();
        if(!tn.matches("[a-zA-Z0-9 ]{2,20}")){
            return;
        }
        Platform.runLater(()-> {
            listViewTeamsToBeCreated.getItems().add(tn);
            textTeamName.clear();
        });
    }

    @FXML
    private void removeTeam() {
        String selected = listViewTeamsToBeCreated.getSelectionModel().getSelectedItem();
        listViewTeamsToBeCreated.getItems().remove(selected);
    }

    @FXML
    private void assignUser() {
        Account account = accountSearchableComboBox.getSelectionModel().getSelectedItem();
        if(account != null){
            if(!listViewAssignedAccounts.getItems().contains(account)){
                listViewAssignedAccounts.getItems().add(account);
            }
            accountSearchableComboBox.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void assignSprint() {
        if (textSprintName != null) {
            Sprint sprint = new Sprint(textSprintName.getText(), datePickerStartDate.getValue(), datePickerEndDate.getValue());
            listViewSprintsToBeCreated.getItems().add(sprint);
        }

            System.out.println("Sprint = " + listViewSprintsToBeCreated.getItems().toString());
    }

    @FXML
    private void removeAssigned() {
        Account acc = listViewAssignedAccounts.getSelectionModel().getSelectedItem();
        if(acc != null){
            listViewAssignedAccounts.getItems().remove(acc);
        }
    }

    @FXML
    private void createProject() {
        System.out.println("CREATEPROJECT");

        String projectName = textProjectName.getText();
        String projectDesc = textDescription.getText();
        if(!projectName.matches("[a-zA-Z0-9\\s ]{3,20}")){
            System.out.println("Check project name");
            return;
        }
        if(projectDesc.length() > 255){
            System.out.println("Check project description");
            return;
        }
        /*Project newProject = new Project();
        newProject.setDescription(projectDesc);
        newProject.setName(projectName);
        System.out.println("New Project created");*/

        Thread thread = new Thread(() -> {
            Project project = new Project(projectName, projectDesc);
            project.addAccount(appStateMain.getAccount());
            if (listViewAssignedAccounts.getItems() != null) {
                for (Account a : listViewAssignedAccounts.getItems()) {
                    project.addAccount(a);
                }
            }

            if (listViewTeamsToBeCreated.getItems() != null) {
                for (String name : listViewTeamsToBeCreated.getItems()) {
                    project.addTeam(new Team(name, project));
                }
            }

            if(listViewSprintsToBeCreated.getItems() != null) {
                for (Sprint sprint : listViewSprintsToBeCreated.getItems()) {
                    sprint.setProject(project);
                    project.addSprint(sprint);
                }
            }
            controller.getProjectDAO().persist(project);

            Platform.runLater(() -> {
                System.out.println("Project added to db");
            });
        });
        thread.start();

        //controller.createProject(projectName, projectDesc, listViewAssignedAccounts.getItems(), listViewTeamsToBeCreated.getItems(), listViewSprintsToBeCreated.getItems());
        listViewAssignedAccounts.getItems().clear();
        listViewTeamsToBeCreated.getItems().clear();
        textProjectName.clear();
        textTeamName.clear();
        textDescription.clear();
        System.out.println("Uusi projekti luotu ja sent to DB tiimeineen");
    }

    private void initDatePickers() {
        datePickerStartDate = new DatePicker(LocalDate.now());
        datePickerEndDate = new DatePicker(LocalDate.now());
    }

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        IViewController viewController = controller.getActiveViewController();
        viewController.handleNavigation(event);
    }
}
