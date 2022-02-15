package r8.view.teamView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import r8.view.navigation.GlobalControllerReference;

import java.io.IOException;

public class CreateTeamViewController {

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        GlobalControllerReference.getInstance().getMainViewController().handleNavigation(event);
    }
}
