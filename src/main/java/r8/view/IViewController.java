package r8.view;

import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import r8.App;

import java.io.IOException;

public interface IViewController {

    App getApp();

    void setApp(App app);

    void handleNavigation(ActionEvent event) throws IOException;

    void handleSubviewNavigation(ActionEvent event) throws IOException;
}
