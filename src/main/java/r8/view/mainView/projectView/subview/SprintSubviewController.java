package r8.view.mainView.projectView.subview;

import javafx.application.Platform;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import r8.controller.Controller;
import r8.controller.IControllerMain;
import r8.model.Account;
import r8.model.Project;
import r8.model.Sprint;

import r8.model.appState.AppState;
import r8.util.UIElementVisibility;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Controller for project sprint subview
 * @author Aarni Pesonen
 */
public class SprintSubviewController {

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
    @FXML
    private VBox vBoxAddSprints = new VBox();
    @FXML
    private Button btnRemoveSprint;

    AppState appState = AppState.INSTANCE;
    IControllerMain controller = new Controller();
    UIElementVisibility visibility = new UIElementVisibility();
    Account account = appState.getLoggedAccount();
    private Project project = appState.getSelectedProject();

    private Set<Project> accountProjects = new HashSet<>();
    private Set<Sprint> projectSprints = new HashSet<>();

    /**
     * Initializes the subview, retrives user related {@link Project} and {@link Sprint} from database.
     */
    @FXML
    private void initialize() {
        visibility.toggleAdminVisibility(vBoxAddSprints, account.getAdmin());
        visibility.toggleAdminVisibility(btnRemoveSprint, account.getAdmin());
        initDatePickers();
        getProjectsFromDB();
        getSprintsFromDB();
        initProjectSprintList();
    }

    /**
     * Adds {@link Sprint} to list that is used to generate DAO to add to db
     */
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

    /**
     * Creates new {@link Sprint} based on user input and adds these to database
     */
    @FXML
    void addSprintsToDB() {
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

    /**
     * Removes {@link Sprint} from listViewSprintToAdd
     */
    @FXML
    void removeSprintFromList() {
        int toRemove = listViewSprintsToAdd.getSelectionModel().getSelectedIndex();
        listViewSprintsToAdd.getItems().remove(toRemove);
    }

    /**
     * Removes {@link Sprint} and {@link Project} association
     */
    @FXML
    void removeSprintFromProject() {

            Sprint toRemove = listViewProjectSprints.getSelectionModel().getSelectedItem();
            int indexToRemove = listViewProjectSprints.getSelectionModel().getSelectedIndex();

            project.removeSprint(toRemove);
            controller.getProjectDAO().update(project);

            Platform.runLater(() -> listViewProjectSprints.getItems().remove(indexToRemove));

        System.out.println(project.getSprints());
    }

    /**
     * Retrieves {@link Sprint} objects from database based on active {@link Project
     */
    private void getSprintsFromDB() {
        projectSprints.addAll(controller.getSprintDAO().getByProject(project));
    }

    /**
     * Retrieves all {@link Project} objects related to user from database
     */
    private void getProjectsFromDB() {
        accountProjects.addAll(controller.getProjectDAO().getByAccount(account));
        ArrayList<Project> projects = new ArrayList<>(accountProjects);
        project = projects.get(0);
    }

    /**
     * Initializes datePickers used to determine {@link Sprint} start and end dates
     */
    private void initDatePickers() {
        datePickerStartDate = new DatePicker(LocalDate.now());
        datePickerEndDate = new DatePicker(LocalDate.now());
    }

    /**
     * Initializes a list of {@link Sprint} objects contained in active {@link Project}
     */
    private void initProjectSprintList() {
        listViewProjectSprints.getItems().addAll(projectSprints);
    }

}
