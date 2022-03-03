package r8.view.taskView;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.control.textfield.TextFields;
import r8.model.Account;
import r8.model.Team;
import r8.model.TextFieldValidator;
import r8.model.task.Task;
import r8.model.task.TaskState;
import r8.model.task.TaskType;

import javax.swing.*;
import java.util.ArrayList;

public class NewTaskViewController {

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



    public void initialize(){
        taskType.getItems().setAll(TaskType.values());

        Account acc1 = new Account("Teemu", "Tallskog", "000", "email");
        Account acc2 = new Account("Teemua", "Tallskoga", "0001", "email");
        Account acc3 = new Account("Teemub", "Tallskogb", "0002", "email");

        comboBoxUser.getItems().add(acc1);
        comboBoxUser.getItems().add(acc2);
        comboBoxUser.getItems().add(acc3);
        TextFieldValidator textFieldValidator = new TextFieldValidator();
        textFieldValidator.setValidation(taskName, "([A-Za-z0-9 ]{1,20})");

    }



    @FXML
    private void saveTask(){
        if(!taskName.getText().matches("([A-Za-z0-9 ]{1,20})")){
            System.out.println("didn't match");
            return;
        }
        Task newTask = new Task(taskName.getText(), TaskState.NOT_STARTED, taskType.getValue());
        newTask.setDescription(descField.getText());
        ArrayList<Account> accountArrayList = new ArrayList<Account>();
        if(listViewAssignedTo.getItems() != null){
            accountArrayList.addAll(listViewAssignedTo.getItems());
        }
    }

    @FXML
    private void AssignUser(){
        Account account = comboBoxUser.getSelectionModel().getSelectedItem();
        if(account != null){
            System.out.println(account.getFirstName());
            listViewAssignedTo.getItems().add(account);
            System.out.println("Added to list");
            Platform.runLater(() -> {
                comboBoxUser.getSelectionModel().clearSelection();
                comboBoxUser.getItems().remove(account);
            });
        }
    }

    @FXML
    private void removeAssigned(){
        Account acc = listViewAssignedTo.getSelectionModel().getSelectedItem();
        if(acc != null){
            listViewAssignedTo.getItems().remove(acc);
            comboBoxUser.getItems().add(acc);
        }
    }

}
