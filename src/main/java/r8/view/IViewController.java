package r8.view;

import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import r8.App;

import java.io.IOException;

/**
 * Interface for login view and main view controllers.
 * Only required methods required for navigation are exposed by the interface.
 *
 * @author Aarni Pesonen
 */
public interface IViewController {

    /**
     * Returns reference to main application
     * @return main application reference
     */
    App getApp();

    /**
     * Sets reference for main application
     * @param app main application reference
     */
    void setApp(App app);

    /**
     * ActionEvent based navigation handling
     * @param event triggering the navigation
     */
    void handleNavigation(ActionEvent event);

    /**
     * ActionEvent based subview navigation handling
     * @param event triggering the subview navigation event
     */
    void handleSubviewNavigation(ActionEvent event);
}
