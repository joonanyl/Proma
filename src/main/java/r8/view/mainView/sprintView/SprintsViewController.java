package r8.view.mainView.sprintView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateMain;
import r8.view.IViewController;

import java.io.IOException;

public class SprintsViewController {

    private final IViewController viewController = AppState.getInstance().getViewController();

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        viewController.handleNavigation(event);
    }
}
