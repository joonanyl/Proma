package r8.view.mainView.teamView;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.apache.logging.log4j.core.util.JsonUtils;
import r8.controller.Controller;
import r8.controller.IControllerMain;
import r8.model.Account;
import r8.model.Project;
import r8.model.Team;
import r8.model.appState.AppState;
import r8.view.IViewController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Aarni Pesonen
 */
public class CreateTeamViewController {

    @FXML
    private Button btnAddTeamToProject;
    @FXML
    private Button btnAddToProject;
    @FXML
    private Button btnNewTask;
    @FXML
    private Button btnRemoveAccountFromList;
    @FXML
    private ComboBox<Account> comboBoxAccount;
    @FXML
    private ComboBox<Project> comboBoxProject;
    @FXML
    private Text labelCreateTeams1;
    @FXML
    private Text labelProjectName;
    @FXML
    private Text labelProjectName1;
    @FXML
    private ListView<Account> listViewTeamAccounts;
    @FXML
    private TextArea textTeamDescription;
    @FXML
    private TextField textTeamName;
    private Set<Account> allAccounts = new HashSet<>();
    private List<Account> accountsToAdd = new ArrayList<>();

    private final IViewController viewController = AppState.getInstance().getViewController();
    private final IControllerMain controller = new Controller();
    private Account account;

    @FXML
    public void initialize() {
        account = AppState.INSTANCE.getAccount();
        updateComboBoxes();
    }

    /**
     * Get account from combobox and adds it to listView
     */
    @FXML
    void addAccountToList() {
        Account account = comboBoxAccount.getSelectionModel().getSelectedItem();
        listViewTeamAccounts.getItems().add(account);
        accountsToAdd.add(account);
    }

    /**
     * Removes previously added account from listView
     */
    @FXML
    void removeAccountFromList(){
        int indexToRemove = listViewTeamAccounts.getSelectionModel().getSelectedIndex();
        Account account = listViewTeamAccounts.getItems().get(indexToRemove);
        listViewTeamAccounts.getItems().remove(indexToRemove);
        accountsToAdd.remove(account);
    }

    /**
     * Updates selected project with new team
     */
    @FXML
    public void addTeamToProject() {
        Project project = comboBoxProject.getSelectionModel().getSelectedItem();
        Team team = new Team(textTeamName.getText(), project);
        for (Account account : accountsToAdd)
            team.addAccount(account);

        team.getAccounts().forEach(System.out::println);
        project.addTeam(team);
        controller.getProjectDAO().update(project);
    }

    /**
     * Used to navigate to other views
     * @param event triggering the navigation
     */
    @FXML
    private void navigate(ActionEvent event) {
        viewController.handleNavigation(event);
    }

    /**
     * Fetches account and project information from database and displays it in comboboxes
     */
    private void updateComboBoxes() {
        Thread thread = new Thread(() -> {
            List<Account> accounts = controller.getAllAccounts();
            List<Project> projects = controller.getProjectDAO().getByAccount(account);

            Platform.runLater(() -> {
                comboBoxProject.getItems().addAll(projects);
                comboBoxAccount.getItems().addAll(accounts);
            });
        });
        thread.start();
    }
}
