package r8.view.mainView.projectView.subview;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import r8.controller.Controller;
import r8.controller.IControllerMain;
import r8.model.Account;
import r8.model.Project;
import r8.model.Sprint;
import r8.model.Team;
import r8.model.appState.AppState;
import r8.model.task.Task;

import java.util.List;
import java.util.Set;

public class OverviewSubviewController {

    @FXML
    private VBox containerProfile;

    @FXML
    private Label labelCurrentSprint;

    @FXML
    private Label labelPersonnelAmount;

    @FXML
    private Label labelSprintsAmount;

    @FXML
    private Label labelTasksComplete;

    @FXML
    private Label labelTeamsAmount;

    private final IControllerMain controller = new Controller();
    private final AppState appState = AppState.getInstance();
    private Project project = appState.getSelectedProject();

    @FXML
    private void initialize() {
        System.out.println("overview init");
        setStatistics();
    }

    private void setInfo() {

    }

    private void setStatistics() {
        Thread thread = new Thread(() -> {
            int tasksComplete = 0;
            List<Sprint> sprints = controller.getSprintDAO().getByProject(project);
            List<Task> tasks = controller.getTaskDAO().getByProject(project);
            Set<Account> accounts = project.getAccounts();
            Set<Team> teams = project.getTeams();
            Platform.runLater(() -> {
                try {
                    labelSprintsAmount.setText(String.valueOf(sprints.size()));
                    labelCurrentSprint.setText(project.getActiveSprint().getName());
                    labelTasksComplete.setText(tasksComplete + " of " + tasks.size());
                    labelPersonnelAmount.setText(String.valueOf(accounts.size()));
                    labelTeamsAmount.setText(String.valueOf(teams.size()));
                } catch (NullPointerException e) {
                    System.out.println("One of project information fields does not yet have a value");
                }
            });
        });
        thread.start();
    }
}
