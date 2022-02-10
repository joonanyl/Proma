package r8.view.taskView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import r8.view.navigation.GetView;
import r8.view.navigation.GlobalControllerRef;

import java.io.IOException;

public class TaskViewController {
    @FXML
    private void navigate(ActionEvent event) throws IOException {
        GlobalControllerRef.getInstance().getMainViewController().handleNavigation(event);
    }
}
