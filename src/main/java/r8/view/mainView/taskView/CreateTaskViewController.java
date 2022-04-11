package r8.view.mainView.taskView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.control.SearchableComboBox;

import r8.controller.Controller;
import r8.controller.IControllerMain;
import r8.model.*;
import r8.model.appState.AppState;
import r8.model.task.TaskState;
import r8.model.task.TaskType;
import r8.util.TextLoader;

import java.util.List;
import java.util.Optional;

public class CreateTaskViewController {

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
    private ListView<CombinedList> listViewAssignedTo;

    @FXML
    private Button btnRemoveAssigned;

    @FXML
    private ComboBox<Project> projectComboBox;

    @FXML
    private TextField createTaskTypeField;

    private final IControllerMain controller = new Controller();

    public void initialize(){
        comboBoxTeam.getItems().addAll(controller.getAllTeams());

        // TODO refactor getting account
        projectComboBox.getItems().addAll(controller.loadProjects(AppState.getInstance().getLoggedAccount()));

        List<Account> accountList = controller.getAllAccounts();
        comboBoxUser.getItems().addAll(accountList);

        updateTaskTypes();

        TextFieldValidator textFieldValidator = new TextFieldValidator();
        textFieldValidator.setValidation(taskName, "([A-Za-z0-9\\s ]{1,20})");

    }

    // TODO does not accept all names, needs testing
    @FXML
    private void createTaskType(){
        String tt = createTaskTypeField.getText();
        if(tt.matches("[a-zA-Z0-9\\s ]{1,10}")){
            controller.createTaskType(tt);
        }
        updateTaskTypes();
    }

    private void updateTaskTypes(){
        List<TaskType> ttList = controller.getAllTaskTypes();
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
        TextLoader textloader = TextLoader.getInstance();
        if(!name.matches("([A-Za-z0-9\\s ]{1,20})")){
            System.out.println("didn't match");
            showAlert(textloader.getResource("invalidInput"), textloader.getResource("invalidTask"), Alert.AlertType.INFORMATION);
            return;
        }
        if(!desc.matches(".{0,200}")){
            showAlert(textloader.getResource("tooLong"), textloader.getResource("longDescription"), Alert.AlertType.INFORMATION);
            return;
        }
        if(tt == null){
            showAlert(textloader.getResource("missingTask"), textloader.getResource("chooseType"), Alert.AlertType.INFORMATION);
            return;
        }
        if(!showAlert(textloader.getResource("confirmation"), textloader.getResource("saveTaskConfirm"), Alert.AlertType.CONFIRMATION)){
            return;
        }
        if(project == null){
            return;
        }
        controller.createTask(name, TaskState.NOT_STARTED, tt, 0,desc, getAccounts(), getTeams(), project);
        showAlert(textloader.getResource("success"), textloader.getResource("saveTaskSuccess"), Alert.AlertType.INFORMATION);
    }

    @FXML
    private void AssignUser(){
        Account account = comboBoxUser.getSelectionModel().getSelectedItem();
        if(account != null){
            if(!listViewAssignedTo.getItems().contains(new CombinedList(account, null))){
                listViewAssignedTo.getItems().add(new CombinedList(account, null));
            }
            comboBoxUser.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void AssignTeam(){
        Team team = comboBoxTeam.getSelectionModel().getSelectedItem();
        if(team != null){
            if(!listViewAssignedTo.getItems().contains(new CombinedList(null, team))){
                listViewAssignedTo.getItems().add(new CombinedList(null, team));
            }
            comboBoxTeam.getSelectionModel().clearSelection();
        }
    }

    private ObservableList<Team> getTeams(){
        ObservableList<CombinedList> combinedLists = listViewAssignedTo.getItems();
        ObservableList<Team> teams = FXCollections.observableArrayList();;
        combinedLists.forEach((item) -> {
            if(!item.checkIfAccount()){
                teams.add(item.getTeam());
            }
        });
        return teams;
    }

    private ObservableList<Account> getAccounts(){
        ObservableList<CombinedList> combinedLists = listViewAssignedTo.getItems();
        ObservableList<Account> accounts = FXCollections.observableArrayList();;
        combinedLists.forEach((item) -> {
            if(item.checkIfAccount()){
                accounts.add(item.getAccount());
            }
        });
        return accounts;
    }

    @FXML
    private void removeAssigned(){
        CombinedList picked = listViewAssignedTo.getSelectionModel().getSelectedItem();
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
