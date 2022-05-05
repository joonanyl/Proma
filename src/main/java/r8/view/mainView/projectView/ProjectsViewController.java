package r8.view.mainView.projectView;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import r8.controller.Controller;
import r8.controller.IControllerAccount;
import r8.controller.IControllerMain;
import r8.model.Account;
import r8.model.Project;
import r8.model.appState.AppState;
import r8.util.UIElementVisibility;
import r8.view.IViewController;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Controller for the projects list view which displays all user {@link Account} {@link Project} data.
 * @author Aarni Pesonen
 */
public class ProjectsViewController {

    @FXML
    private Button projectsViewTooltip;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label labelCreatedBy;
    @FXML
    private Label labelProjectName;
    @FXML
    private Label labelPersonnelAmount;
    @FXML
    private HBox projectNavBar;

    @FXML
    private ListView<CustomProjectComponentController> projectsListView;

    private final UIElementVisibility visibility = new UIElementVisibility();
    private final ProjectsViewController projectsViewController = this;
    private final IControllerAccount controllerAccount = new Controller();
    private final IControllerMain controller = new Controller();
    private final IViewController viewController = controller.getActiveViewController();
    private Set<Project> allProjects;

    /**
     * Initializes the projects list view
     */
    @FXML
    public void initialize(){
        labelPersonnelAmount = new Label();
        List<Project> projectsList = controller.loadProjects(AppState.getInstance().getAccount());
        allProjects = new HashSet<>();
        visibility.setTooltipVisibility(projectsViewTooltip);

        retrieveProjects();
        updateView();
        listViewChangeListener();
    }

    /**
     * Used in subview navigation
     * @param event triggering the navigation
     */
    @FXML
    void navigate(ActionEvent event) {
        try {
            AppState.getInstance().setSelectedProject(projectsListView.getSelectionModel().getSelectedItem().getProject());
            viewController.handleSubviewNavigation(event);
        } catch (NullPointerException e) {
            System.out.println("No task selected or the selected task is null.");
        }
    }

    /**
     * Navigates to create project view
     * @param event triggering the navigation
     */
    @FXML
    void navigateNewProject(ActionEvent event) {
        viewController.handleSubviewNavigation(event);
    }

    private void listViewChangeListener(){
        projectsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                labelProjectName.setText(newValue.getProject().getName());
                labelPersonnelAmount.setText(String.valueOf(newValue.getProject().getAccounts().size()));
                //labelTaskState.setText(newValue.getTask().getTaskStateString());
            }
        });
    }

    /**
     * Retrieves all {@link Account} related {@link Project} objects from database
     */
    @Transactional
    public void retrieveProjects(){

        Account loggedAccount = AppState.getInstance().getLoggedAccount();
        allProjects.addAll(controller.getProjectDAO().getByAccount(loggedAccount));

        updateView();
    }

    /**
     * Updates the list of {@link CustomProjectComponentController} objects displayed
     */
    private void updateView(){
        System.out.println("Update View called");
        Platform.runLater(()->{
            projectsListView.getItems().clear();
            if(allProjects != null){
                allProjects.forEach(project -> projectsListView.getItems().add(new CustomProjectComponentController(project, this)));
            }
        });
    }
}
