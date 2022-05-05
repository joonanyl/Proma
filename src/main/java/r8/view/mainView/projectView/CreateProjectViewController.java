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

/**
 * Controller for project creation view
 * @author Aarni Pesonen
 */
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

    /**
     * Initializes the project creation view
     */
    public void initialize() {
        List<Account> accountList = controller.getAllAccounts();
        accountList.remove(AppState.getInstance().getLoggedAccount());
        accountList.forEach((account)-> accountSearchableComboBox.getItems().add(account));

        btnCreateProject1.setOnAction(event -> createProject());
        btnCreateProject.setOnAction(event -> createProject());

        initDatePickers();
    }

    /**
     * Adds team creation info to list of {@link Team} to be created
     * along with the {@link Project}
     */
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

    /**
     * Removes {@link Team} info from list of teams to be created
     */
    @FXML
    private void removeTeam() {
        String selected = listViewTeamsToBeCreated.getSelectionModel().getSelectedItem();
        listViewTeamsToBeCreated.getItems().remove(selected);
    }

    /**
     * Associates {@link Account} with the active {@link Project}
     */
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

    /**
     * Associates {@link Sprint} with the active {@link Project}
     */
    @FXML
    private void assignSprint() {
        if (textSprintName != null) {
            Sprint sprint = new Sprint(textSprintName.getText(), datePickerStartDate.getValue(), datePickerEndDate.getValue());
            listViewSprintsToBeCreated.getItems().add(sprint);
        }

            System.out.println("Sprint = " + listViewSprintsToBeCreated.getItems().toString());
    }

    /**
     * Removes {@link Account} from a list of personnel to be assigned to selected {@link Project}
     * at the time of creation
     */
    @FXML
    private void removeAssigned() {
        Account acc = listViewAssignedAccounts.getSelectionModel().getSelectedItem();
        if(acc != null){
            listViewAssignedAccounts.getItems().remove(acc);
        }
    }

    /**
     * Attempts to create {@link Project} with user defined associations
     * Checks if input data matches the required criteria
     */
    @FXML
    private void createProject() {
        System.out.println("createProject() called");

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

            Platform.runLater(() -> System.out.println("Project added to db"));
        });
        thread.start();

        clearFields();
        System.out.println("Uusi projekti luotu ja sent to DB tiimeineen");
    }

    /**
     * Initializes date pickers
     */
    private void initDatePickers() {
        datePickerStartDate = new DatePicker(LocalDate.now());
        datePickerEndDate = new DatePicker(LocalDate.now());
    }

    /**
     * Clear user input fields
     */
    private void clearFields() {
        listViewAssignedAccounts.getItems().clear();
        listViewTeamsToBeCreated.getItems().clear();
        textProjectName.clear();
        textTeamName.clear();
        textDescription.clear();
        textSprintName.clear();
        datePickerStartDate.setValue(null);
        datePickerEndDate.setValue(null);
        listViewSprintsToBeCreated.getItems().clear();
    }

    /**
     * Used to navigate away from project creation view
     * @param event triggering the navigation
     */
    @FXML
    private void navigate(ActionEvent event) {
        IViewController viewController = controller.getActiveViewController();
        viewController.handleNavigation(event);
    }
}
