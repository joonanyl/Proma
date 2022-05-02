package r8.view.mainView.taskView;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.controlsfx.control.SearchableComboBox;

import r8.controller.Controller;
import r8.controller.IControllerMain;
import r8.model.*;
import r8.model.appState.AppState;
import r8.model.task.TaskState;
import r8.model.task.TaskType;
import r8.util.UIElementVisibility;
import r8.util.lang.LanguageHandler;

import java.util.*;

public class CreateTaskViewController {

    @FXML
    private VBox assignTaskVbox;
    @FXML
    private SearchableComboBox<Account> comboBoxUser;
    @FXML
    private SearchableComboBox<Team> comboBoxTeam;
    @FXML
    private TextField taskName;
    @FXML
    private ComboBox<TaskType> taskType;
    @FXML
    private TextArea descField;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnAssignUser;
    @FXML
    private ListView<CombinedObject> listViewAssignedTo;
    @FXML
    private Button btnRemoveAssigned;
    @FXML
    private ComboBox<Project> projectComboBox;
    @FXML
    private TextField createTaskTypeField;

    private final IControllerMain controller = new Controller();
    private final UIElementVisibility visibility = new UIElementVisibility();
    List<TaskType> ttList;

    public void initialize(){

        comboBoxTeam.getItems().addAll(controller.getAllTeams());

        // TODO refactor getting account
        projectComboBox.getItems().addAll(controller.loadProjects(AppState.getInstance().getLoggedAccount()));

        List<Account> accountList = controller.getAllAccounts();
        Account loggedAccount = AppState.getInstance().getLoggedAccount();
        comboBoxUser.getItems().addAll(accountList);

        visibility.toggleAdminVisibility(assignTaskVbox, loggedAccount.getAdmin());
        updateTaskTypes();

        TextFieldValidator.setValidation(taskName, "([A-Za-z0-9\\s ]{1,20})");
    }

    // TODO does not accept all names, needs testing
    @FXML
    private void createTaskType(){
        String tt = createTaskTypeField.getText();
        ArrayList<String> ttNames = new ArrayList<>();
        for (TaskType e : ttList)
            ttNames.add(e.getName());

        if(tt.matches("[a-zA-Z0-9\\s ]{1,20}") && !ttNames.contains(tt)){
            controller.createTaskType(tt);
        }
        updateTaskTypes();
    }

    private void updateTaskTypes(){
        ttList = controller.getAllTaskTypes();
        if(ttList != null){
            taskType.getItems().clear();
            taskType.getItems().addAll(ttList);
        }
    }

    @FXML
    private void saveTask(){
        TaskType tt = taskType.getSelectionModel().getSelectedItem();
        String name = taskName.getText();
        String desc = descField.getText();
        Project project = projectComboBox.getSelectionModel().getSelectedItem();
        if(!name.matches("([A-Za-z0-9\\s ]{1,20})")){
            System.out.println("didn't match");
            showAlert(LanguageHandler.getText("invalidInput"), LanguageHandler.getText("invalidTask"), Alert.AlertType.INFORMATION);
            return;
        }
        if(!desc.matches(".{0,200}")){
            showAlert(LanguageHandler.getText("tooLong"), LanguageHandler.getText("longDescription"), Alert.AlertType.INFORMATION);
            return;
        }
        if(tt == null){
            showAlert(LanguageHandler.getText("missingTask"), LanguageHandler.getText("chooseType"), Alert.AlertType.INFORMATION);
            return;
        }
        if(!showAlert(LanguageHandler.getText("confirmation"), LanguageHandler.getText("saveTaskConfirm"), Alert.AlertType.CONFIRMATION)){
            return;
        }
        if(project == null){
            return;
        }
        controller.createTask(name, TaskState.NOT_STARTED, tt, 0,desc, getAccounts(), getTeams(), project);
        showAlert(LanguageHandler.getText("success"), LanguageHandler.getText("saveTaskSuccess"), Alert.AlertType.INFORMATION);
    }

    @FXML
    private void AssignUser(){
        Account account = comboBoxUser.getSelectionModel().getSelectedItem();
        if(account != null){
            if(!listViewAssignedTo.getItems().contains(new CombinedObject(account))){
                listViewAssignedTo.getItems().add(new CombinedObject(account));
            }
            comboBoxUser.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void AssignTeam(){
        Team team = comboBoxTeam.getSelectionModel().getSelectedItem();
        if(team != null){
            if(!listViewAssignedTo.getItems().contains(new CombinedObject(team))){
                listViewAssignedTo.getItems().add(new CombinedObject(team));
            }
            comboBoxTeam.getSelectionModel().clearSelection();
        }
    }

    private Set<Team> getTeams(){
        ObservableList<CombinedObject> combinedObjects = listViewAssignedTo.getItems();
        Set<Team> teams = new HashSet<>();
        combinedObjects.forEach((item) -> {
            if(!item.isAccount()){
                teams.add(item.getTeam());
            }
        });
        return teams;
    }

    private Set<Account> getAccounts(){
        ObservableList<CombinedObject> combinedObjects = listViewAssignedTo.getItems();
        Set<Account> accounts = new HashSet<>();
        combinedObjects.forEach((item) -> {
            if(item.isAccount()){
                accounts.add(item.getAccount());
            }
        });
        return accounts;
    }

    @FXML
    private void removeAssigned(){
        CombinedObject picked = listViewAssignedTo.getSelectionModel().getSelectedItem();
        if(picked != null){
            listViewAssignedTo.getItems().remove(picked);
        }
    }

    private boolean showAlert(String title, String text, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(text);

        Optional<ButtonType> result = alert.showAndWait();
        if(!result.isPresent() || result.get() != ButtonType.OK) {
            return false;
        } else {
            return true;
        }
    }
}
