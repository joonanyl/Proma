package r8.view.mainView.projectView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class ProjectsViewController {

    @FXML
    private Button btnActiveProjects;

    @FXML
    private Button btnCompletedProjects;

    @FXML
    private Button btnGoToProjectView;

    @FXML
    private Button buttonAllProjects;

    @FXML
    private ListView<?> listViewMyProjects;

    @FXML
    private HBox projectNavBar;

    @FXML
    void navigate(ActionEvent event) {

    }

}
