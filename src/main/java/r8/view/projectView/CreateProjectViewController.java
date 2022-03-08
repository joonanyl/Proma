package r8.view.projectView;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import r8.model.Project;
import r8.view.navigation.GlobalControllerReference;

import java.io.IOException;

public class CreateProjectViewController {

    @FXML
    private TextField textProjectName;

    @FXML
    private TextArea textDescription;

    @FXML
    private TextField textTeamName;

    @FXML
    private ListView<String> listViewTeamsToBeCreated;

    @FXML
    private ListView listViewAssignedTo;

    @FXML
    private TextField textSprintName;

    @FXML
    private boolean createTeam(Project project){
        String tn = textTeamName.getText();
        if(tn.matches("[a-zA-Z0-9]{2,20}")){
            return false;
        }
        //project.addTeam(new Team(tn, project));
        return true;
    }

    @FXML
    private void createProject(){
        String projectName = textProjectName.getText();
        String projectDesc = textDescription.getText();
        if(!projectName.matches("[a-zA-Z0-9]{3,20}")){
            return;
        }
        if(projectDesc.length() > 255){
            return;
        }
        Project newProject = new Project();
        newProject.setDescription(projectDesc);
        newProject.setName(projectName);
        ObservableList teamslist = listViewTeamsToBeCreated.getItems();
        teamslist.forEach((item) ->{
            if(!createTeam(newProject)){
                return;
            }
        } );
    }


    @FXML
    private void navigate(ActionEvent event) throws IOException {
        GlobalControllerReference.getInstance().getMainViewController().handleNavigation(event);
    }
}
