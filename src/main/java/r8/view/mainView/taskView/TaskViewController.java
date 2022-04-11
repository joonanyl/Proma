package r8.view.mainView.taskView;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import org.controlsfx.control.SearchableComboBox;
import r8.controller.Controller;
import r8.controller.IControllerMain;
import r8.model.Account;
import r8.model.CombinedList;
import r8.model.Team;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateMain;
import r8.model.task.Task;
import r8.model.task.TaskState;
import r8.util.TextLoader;
import r8.view.IViewController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TaskViewController {

    @FXML
    private Label labelTaskName;

    @FXML
    private Label labelTaskType;

    @FXML
    private ComboBox<TaskState> comboBoxTaskStatus;

    @FXML
    private Label labelCreatedBy;

    @FXML
    private TextArea textAreaDescription;

    @FXML
    private ListView<CombinedList> assignedToList;

    @FXML
    private SearchableComboBox<CombinedList> assignNewComboBox;

    // TODO selected task needs this reference, maybe refactor
    private final IAppStateMain appStateMain = AppState.getInstance();
    private final IControllerMain controller = new Controller();
    private final IViewController viewController = controller.getActiveViewController();

    private Task selectedTask = null;

    @FXML
    private void navigate(ActionEvent event) throws IOException {
       viewController.handleNavigation(event);
    }

    public void initialize(){
        this.selectedTask = appStateMain.getSelectedTask();
        comboBoxTaskStatus.getItems().addAll(TaskState.values());
        List<Account> accounts = controller.getAllAccounts();
        List<Team> teams = controller.getAllTeams();
        if(teams != null){
            assignNewComboBox.getItems().addAll(transformObjects(null, new HashSet<>(teams)));
        }
        if(accounts != null){
            assignNewComboBox.getItems().addAll(transformObjects(new HashSet<>(accounts), null));
        }

        if(this.selectedTask != null){
            assignedToList.getItems().addAll(transformObjects(null, selectedTask.getTeams()));
            assignedToList.getItems().addAll(transformObjects(selectedTask.getAccounts(), null));
            labelTaskName.setText(this.selectedTask.getName());
            labelTaskType.setText(this.selectedTask.getTaskType().toString());
            comboBoxTaskStatus.setValue(TaskState.valueOf(selectedTask.getTaskStateString()));
            Set<Account> accountsSet = selectedTask.getAccounts();
            if(accountsSet.size() > 0){
                labelCreatedBy.setText(TextLoader.getInstance().getResource("createdBy") + " " + accountsSet.iterator().next().toString());
            }
            textAreaDescription.setText(selectedTask.getDescription());
        }
    }

    /**
     * Requires either account set or team set to be null, turns it into a combinedList list and returns
     * @param accounts set<Account> if teams is null, otherwise this should be NULL
     * @param teams set<Team> if Account is null, otherwise this should be NULL
     * @return
     */
    private List<CombinedList> transformObjects(Set<Account> accounts, Set<Team> teams){
        List<CombinedList> list = new ArrayList<>();
        if(accounts != null){
            accounts.forEach((account) -> {
                list.add(new CombinedList(account, null));
            });
        }else if(teams != null){
            teams.forEach((team) -> {
                list.add(new CombinedList(null, team));
            });
        }
        return list;
    }

    @FXML
    private void assignNew(){
        CombinedList selected = assignNewComboBox.getSelectionModel().getSelectedItem();
        assignedToList.getItems().add(selected);
        assignNewComboBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void removeAssigned(){
       CombinedList selected = assignedToList.getSelectionModel().getSelectedItem();
        if(selected != null){
            if(assignedToList.getItems().contains(selected)){
                assignedToList.getItems().remove(selected);
            }
        }
    }

    private Set<Account> getAssignedAccounts(){
        Set<Account> accounts = new HashSet<Account>();
        ObservableList<CombinedList> assigned = assignedToList.getItems();
        assigned.forEach((item) -> {
            if(item.checkIfAccount()){
                accounts.add(item.getAccount());
            }
        });
        return accounts;
    }

    private Set<Team> getAssignedTeams(){
        Set<Team> teams = new HashSet<Team>();
        ObservableList<CombinedList> assigned = assignedToList.getItems();
        assigned.forEach((item) -> {
            if(!item.checkIfAccount()){
                teams.add(item.getTeam());
            }
        });
        return teams;
    }

    @FXML
    private void saveTask(){
        if(this.selectedTask == null){
            return;
        }
        this.selectedTask.setDescription(textAreaDescription.getText());
        TaskState state = comboBoxTaskStatus.getSelectionModel().getSelectedItem();
        if(state != null){
            this.selectedTask.setTaskState(comboBoxTaskStatus.getSelectionModel().getSelectedItem());
        }
        this.selectedTask.setAccounts(getAssignedAccounts());
        this.selectedTask.setTeams(getAssignedTeams());
        controller.updateTask(this.selectedTask);
    }
}
