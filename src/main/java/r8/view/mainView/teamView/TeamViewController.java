package r8.view.mainView.teamView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import r8.model.appState.AppState;
import r8.view.IViewController;

import java.io.IOException;

public class TeamViewController {

    private final IViewController viewController = AppState.getInstance().getViewController();

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        viewController.handleSubviewNavigation(event);
    }
}
