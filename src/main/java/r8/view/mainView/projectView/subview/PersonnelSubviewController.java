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

    private final Account account = AppState.getInstance().getAccount();
    private Set<Account> allAccounts = new HashSet<>();
    private Set<Account> projectAccounts = new HashSet<>();
    private Set<Project> accountProjects = new HashSet<>();

    IControllerMain controller = new Controller();
    private Project project;

    @FXML
    private void initialize() {
        getAccountsFromDB();
        getProjectsFromDB();

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

                controller.getProjectDAO().update(project);

            Platform.runLater(() -> System.out.println("Project updated."));
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
        controller.getProjectDAO().remove(project);
    }

    private void getAccountsFromDB() {
        Thread thread = new Thread(() -> {

            allAccounts.addAll(controller.getAllAccounts());

            Platform.runLater(() -> comboBoxPerson.getItems().addAll(allAccounts));
        });
        thread.start();
    }

    private void getProjectsFromDB() {
        accountProjects.addAll(controller.getProjectDAO().getByAccount(account));
    }

    private void initProjectAccountList() {
        ArrayList<Project> projects = new ArrayList<>(accountProjects);
        project = projects.get(0);
        listViewProjectPersonnel.getItems().addAll(project.getAccounts());
    }
}

