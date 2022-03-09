package r8.view.projectView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import r8.model.Account;
import r8.model.Sprint;
import r8.model.Team;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateMain;
import r8.model.task.Task;
import r8.model.task.TaskState;
import r8.model.task.TaskType;

import java.io.IOException;

public class ProjectViewController {

    final IAppStateMain appStateMain = AppState.getInstance();

    @FXML
    private ListView<Sprint> listViewSprint;

    @FXML
    private ListView<Task> listViewTask;

    @FXML
    private ListView<Account> listViewAccount;

    @FXML
    private ListView<Team> listViewTeam;

    @FXML
    private TextArea textAreaSprint;

    @FXML
    private TextArea textAreaTask;

    @FXML
    private Label labelProjectName;

    @FXML
    private VBox vBoxProjectSprints;

    public void initialize(){
        Task task = new Task("name", TaskState.NOT_STARTED, new TaskType("type"), 0, "desc");
        listViewTask.getItems().add(task);
        textAreaTask.setEditable(false);
        textAreaSprint.setEditable(false);
        setListeners();
        admin = appStateMain.getIsAdmin();
        adminVisibility(admin);
    }


    private boolean admin;




    private void adminVisibility(boolean isAdmin) {
        if (!admin) {
            vBoxProjectSprints.setVisible(false);
            vBoxProjectSprints.setManaged(false);
        }
    }

    private void setListeners(){
        listViewTask.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
            @Override
            public void changed(ObservableValue<? extends Task> observable, Task oldValue, Task newValue) {
                selectTask(newValue);
            }
        });
        listViewSprint.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Sprint>() {
            @Override
            public void changed(ObservableValue<? extends Sprint> observable, Sprint oldValue, Sprint newValue) {
                selectSprint(newValue);
            }
        });
    }

    private void selectTask(Task task){
        textAreaTask.setText(task.getDescription());
    }

    private void selectSprint(Sprint sprint){
        //sprintTextField.setText();
    }


    @FXML
    private void navigate(ActionEvent event) throws IOException {
        appStateMain.getMainViewController().handleNavigation(event);
    }
}
