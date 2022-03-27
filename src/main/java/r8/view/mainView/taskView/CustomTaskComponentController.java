package r8.view.mainView.taskView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import r8.controller.Controller;
import r8.model.CombinedList;
import r8.model.appState.AppState;
import r8.model.task.Task;
import r8.model.task.TaskState;
import r8.model.task.TaskType;
import r8.view.IViewController;

import java.io.IOException;
import java.util.ArrayList;

public class CustomTaskComponentController extends GridPane {
    @FXML
    private Label labelName;
    @FXML
    private ComboBox<TaskState> comboBoxState;
    @FXML
    private ListView listViewAssigned;
    @FXML
    private Label labelType;
    @FXML
    private MenuButton menuButton;
    @FXML
    private MenuItem deleteTask;

    private Task task;
    private TasksViewController controller;

    public CustomTaskComponentController(Task task, TasksViewController controller){
        this.controller = controller;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/task-custom-component.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        if(task != null){
            this.task = task;
            labelName.setText(task.getName());
            comboBoxState.getItems().setAll(TaskState.values());
            comboBoxState.setValue(TaskState.valueOf(task.getTaskStateString()));
            ArrayList<CombinedList> arrayList = new ArrayList<>();
            task.getTeams().forEach(team -> {
                CombinedList t = new CombinedList(null, team);
                arrayList.add(t);
            });
            task.getAccounts().forEach(account -> {
                CombinedList a = new CombinedList(account, null);
                arrayList.add(a);
            });
            if(arrayList.size() == 0){
                listViewAssigned.setFocusTraversable(false);
                listViewAssigned.setMouseTransparent(true);
            }else{
                arrayList.forEach(o ->{
                    listViewAssigned.getItems().add(0, new Text(o.toString()));
                });
            }
        }
    }

    public Task getTask(){
        return this.task;
    }
    private void setListener(){
        comboBoxState.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                //todo updateTaskState
            }
        });

    }

    @FXML
    private void editTask(ActionEvent event) throws IOException{
        AppState.getInstance().setSelectedTask(task);
        controller.navigateNewTask(event);
    }

    @FXML
    private void deleteTask(){
        controller.deleteTask(this.task);
    }
}
