package r8.view.taskView;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.control.SearchableComboBox;
import r8.model.Account;
import r8.model.Project;
import r8.model.Team;
import r8.model.TextFieldValidator;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateMain;
import r8.model.task.Task;
import r8.model.task.TaskState;
import r8.model.task.TaskType;

import java.util.ArrayList;
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
    private ListView<Account> listViewAssignedTo;


    @FXML
    private Button btnRemoveAssigned;

    @FXML
    private ComboBox<Project> projectComboBox;

    @FXML
    private TextField createTaskTypeField;


    private final IAppStateMain appStateMain = AppState.getInstance();

    public void initialize(){
        //taskType.getItems().setAll(TaskType.values());
        projectComboBox.getItems().addAll(appStateMain.getProjects());
        comboBoxUser.getItems().addAll(appStateMain.getAllAccounts());
        updateTaskTypes();

        TextFieldValidator textFieldValidator = new TextFieldValidator();
        textFieldValidator.setValidation(taskName, "([A-Za-z0-9 ]{1,20})");

    }

    @FXML
    private void createTaskType(){
        String tt = createTaskTypeField.getText();
        if(tt.matches("[a-zA-Z0-9 ]{1,10}")){
            appStateMain.createTaskType(tt);
        }
        updateTaskTypes();
    }

    private void updateTaskTypes(){
        List<TaskType> ttList = appStateMain.getAllTaskTypes();
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
        if(!name.matches("([A-Za-z0-9 ]{1,20})")){
            System.out.println("didn't match");
            showAlert("Invalid input", "Invalid task name!", Alert.AlertType.INFORMATION);
            return;
        }
        if(!desc.matches(".{0,200}")){
            showAlert("Too long", "Your description is too long", Alert.AlertType.INFORMATION);
            return;
        }
        if(tt == null){
            showAlert("Missing task type" ,"Please choose or create and choose a task type", Alert.AlertType.INFORMATION);
            return;
        }
        if(!showAlert("Confirmation", "Are you sure you want to save this task?", Alert.AlertType.CONFIRMATION)){
            return;
        }
        appStateMain.createTask(name, TaskState.NOT_STARTED, tt, 0,desc, listViewAssignedTo.getItems(), null);
        showAlert("Success", "Successfully saved this task!", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void AssignUser(){
        Account account = comboBoxUser.getSelectionModel().getSelectedItem();
        if(account != null){
            if(!listViewAssignedTo.getItems().contains(account)){
                listViewAssignedTo.getItems().add(account);
            }
            comboBoxUser.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void removeAssigned(){
        Account acc = listViewAssignedTo.getSelectionModel().getSelectedItem();
        if(acc != null){
            listViewAssignedTo.getItems().remove(acc);
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
