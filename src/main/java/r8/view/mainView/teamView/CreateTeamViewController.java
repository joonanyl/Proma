package r8.view.mainView.teamView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import r8.App;

import java.io.IOException;

public class CreateTeamViewController {

    //private final IViewController viewController = AppState.getInstance().getViewController();

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        App.handleNavigation(event);
    }
}
