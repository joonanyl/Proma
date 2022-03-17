package r8.view.mainView.projectView.subview;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class TeamSubviewController {

    @FXML
    private Button btnAddTeam;

    @FXML
    private Button btnRemoveTeam;

    @FXML
    private Button buttonCreateTeams;

    @FXML
    private ListView<?> listViewExistingTeams;

    @FXML
    private ListView<?> listViewTeamsToAdd;

    @FXML
    private TextField textFieldTeamName;
}
