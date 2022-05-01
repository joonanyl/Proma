package r8.view.mainView.projectView.subview;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.controlsfx.control.SearchableComboBox;
import r8.controller.Controller;
import r8.controller.IControllerMain;
import r8.model.Account;
import r8.model.Project;
import r8.model.appState.AppState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonnelSubviewController {

    @FXML
    private Button btnAddPerson;
    @FXML
    private Button btnAddPersonnel;
    @FXML
    private Button btnRemoveFromProject;
    @FXML
    private Button btnRemovePerson;
    @FXML
    private SearchableComboBox<Account> comboBoxPerson;
    @FXML
    private ListView<Account> listViewPersonnelToAdd;
    @FXML
    private ListView<Account> listViewProjectPersonnel;

    private final AppState appState = AppState.getInstance();
    private final Account account = appState.getAccount();
    private ArrayList<Account> allAccounts = new ArrayList<>();
    private ArrayList<Account> projectAccounts = new ArrayList<>();
    private List<Project> accountProjects = new ArrayList<>();

    IControllerMain controller = new Controller();
    private Project project = appState.getSelectedProject();

    @FXML
    private void initialize() {
        project = appState.getSelectedProject();

        getProjectsFromDB();
        getAccountsFromDB();

        initProjectAccountList();
    }

    @FXML
    void addPersonToList() {
        listViewPersonnelToAdd.getItems().add(comboBoxPerson.getSelectionModel().getSelectedItem());
    }

    @FXML
    void addPersonsToProject() {

        Thread thread = new Thread(() -> {
            List<Account> acs = listViewPersonnelToAdd.getItems();
            for (Account account : acs)
                project.addAccount(account);

            System.out.println("adding to bd accounts: " + acs.toString());
                controller.getProjectDAO().update(project);

            Platform.runLater(() -> {
                //listViewProjectPersonnel.getItems().addAll(acs);
                System.out.println("Project updated.");
            });
        });
        thread.start();
    }

    @FXML
    void removePersonFromList() {
        int toRemove = listViewPersonnelToAdd.getSelectionModel().getSelectedIndex();
        listViewPersonnelToAdd.getItems().remove(toRemove);
    }

    @FXML
    void removePersonFromProject() {
        Account toRemove = listViewProjectPersonnel.getSelectionModel().getSelectedItem();
        project.removeAccount(toRemove);
        controller.getProjectDAO().removeAccountAssociation(account, project);
    }

    private void getProjectsFromDB() {
        //not used
        accountProjects.addAll(controller.getProjectDAO().getByAccount(account));
        projectAccounts.addAll(project.getAccounts());
    }

    private void getAccountsFromDB() {
        Thread thread = new Thread(() -> {

            allAccounts.addAll(controller.getAllAccounts());

            Platform.runLater(() -> comboBoxPerson.getItems().addAll(allAccounts));

        });
        thread.start();
    }

    private void initProjectAccountList() {
        ArrayList<Project> projects = new ArrayList<>(accountProjects);

        listViewProjectPersonnel.getItems().addAll(projectAccounts);
    }
}

