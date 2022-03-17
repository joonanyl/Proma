package r8.view.taskView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import r8.model.CombinedList;
import r8.model.task.Task;
import r8.model.task.TaskState;

import javax.swing.text.LabelView;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class CustomTaskComponentController extends VBox {
    @FXML
    private Label labelName;
    @FXML
    private ComboBox comboBoxState;
    @FXML
    private VBox vboxAssigned;
    @FXML
    private Label labelType;

    private Task task;

    public CustomTaskComponentController(Task task){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "custom-task-component.fxml"));
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
            comboBoxState.getSelectionModel().select(task.getTaskState());
            ArrayList<CombinedList> arrayList = new ArrayList<>();
            task.getTeams().forEach(team -> {
                CombinedList t = new CombinedList(null, team);
                arrayList.add(t);
            });
            task.getAccounts().forEach(account -> {
                CombinedList a = new CombinedList(account, null);
                arrayList.add(a);
            });
            arrayList.forEach(o ->{
                vboxAssigned.getChildren().add(0, new Text(o.toString()));
            });
        }
    }

    private void setListener(){
        comboBoxState.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                
            }
        });
    }
}
