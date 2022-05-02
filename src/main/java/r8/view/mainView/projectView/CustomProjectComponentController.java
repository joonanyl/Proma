package r8.view.mainView.projectView;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import r8.model.CombinedObject;
import r8.model.Project;
import r8.model.appState.AppState;
import r8.model.task.TaskState;

import java.io.IOException;
import java.util.ArrayList;

public class CustomProjectComponentController extends GridPane {
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
    private MenuItem deleteProject;

    private ProjectsViewController controller;
    private Project project;

    public CustomProjectComponentController(Project project, ProjectsViewController controller){
        this.controller = controller;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/project-custom-component.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        if(project != null){
            this.project = project;
            labelName.setText(project.getName());
            comboBoxState.getItems().setAll(TaskState.values());
            //comboBoxState.setValue(TaskState.valueOf(project.getTaskStateString()));
            ArrayList<CombinedObject> arrayList = new ArrayList<>();
            project.getTeams().forEach(team -> {
                CombinedObject t = new CombinedObject(team);
                arrayList.add(t);
            });
            project.getAccounts().forEach(account -> {
                CombinedObject a = new CombinedObject(account);
                arrayList.add(a);
            });
            if(arrayList.size() == 0){
                listViewAssigned.setFocusTraversable(false);
                listViewAssigned.setMouseTransparent(true);
            }else{
                arrayList.forEach(o -> listViewAssigned.getItems().add(0, new Text(o.toString())));
            }
        }
    }

    @FXML
    private void editProject(ActionEvent event){
        AppState.getInstance().setSelectedProject(project);
        controller.navigateNewProject(event);
    }

    public Project getProject(){
        return this.project;
    }
}
