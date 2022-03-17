package r8.view.mainView.projectView.subview;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class SprintSubviewController {

    @FXML
    private Button btnAddSprint;

    @FXML
    private Button btnCreateSprint;

    @FXML
    private Button btnRemoveSprint;

    @FXML
    private ListView<?> listViewExistingSprints;

    @FXML
    private ListView<?> listViewSprintsToAdd;

    @FXML
    private TextField tesxtFieldSprintName;
}
