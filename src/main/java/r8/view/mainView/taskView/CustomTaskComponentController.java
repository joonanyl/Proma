package r8.view.mainView.taskView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import r8.controller.Controller;
import r8.controller.IControllerMain;
import r8.model.CombinedObject;
import r8.model.appState.AppState;
import r8.model.task.Task;
import r8.model.task.TaskState;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller for {@link Task} component
 * @author Teemu Tallskog
 */
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
            comboBoxState.valueProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    updateTask((TaskState) newValue);
                }
            });
            ArrayList<CombinedObject> arrayList = new ArrayList<>();
            task.getTeams().forEach(team -> {
                CombinedObject t = new CombinedObject(team);
                arrayList.add(t);
            });
            task.getAccounts().forEach(account -> {
                CombinedObject a = new CombinedObject(account);
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


    private void updateTask(TaskState taskState){
        System.out.println("Updated task");
        IControllerMain controllerMain = new Controller();
        this.task.setTaskStateString(taskState.toString());
        this.task.setTaskState(taskState);
        controllerMain.updateTask(this.task);
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
