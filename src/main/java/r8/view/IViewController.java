package r8.view;

import javafx.event.ActionEvent;
import r8.App;

import java.io.IOException;

public interface IViewController {

    App getApp();

    void setApp(App app);

    void handleNavigation(ActionEvent event);

    void handleSubviewNavigation(ActionEvent event);
}
