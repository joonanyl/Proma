package r8.view.navigation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import r8.model.appState.AppState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Stores breadcrumb navigation info
 * generates breadcrumbObjects for each navigation step
 * and stores them in a list that is used to generate buttons
 * for UI breadcrumb element
 *
 * @author Aarni Pesonen
 */

public class BreadcrumbBar {

    private ObservableList<Button> breadcrumbButtons = FXCollections.observableArrayList();
    private List<BreadcrumbObject> breadcrumbs = new ArrayList<>();

    private String currentView;

    /**
     * Creates list of buttons to populate the UI breadcrumb element
     * @return breadcrumb buttons in ObservableList format
     */
    public ObservableList<Button> createBreadcrumbs () {

        for (BreadcrumbObject bcObj : breadcrumbs) {
            String[] buttonInfo = bcObj.getButtonInfo();
            Button bcButton = new Button(buttonInfo[1]);
            bcButton.setUserData(buttonInfo[0]);

            bcButton.setOnAction(event -> {
                Node eventSource = (Node) event.getSource();
                try {
                    if(!Objects.equals(eventSource.getUserData(), currentView))
                    AppState.getInstance().getViewController().handleNavigation(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            System.out.println("hbox buttons in list: " + breadcrumbButtons);
            breadcrumbButtons.add(bcButton);
            System.out.println("hbox buttons in list after adding: " + breadcrumbButtons);
        }
        return breadcrumbButtons;
    }

    /**
     * Extracts relevant breadcrumb information from an event source
     * @param event button press that trigger the event
     * @return updated list of breadcrumbs
     */
    public ObservableList<Button> add(ActionEvent event) {

        final Node eventSource = (Node) event.getSource();
        Button target = (Button) event.getTarget();
        if (!Objects.equals(eventSource.getUserData(), currentView)) {
            currentView = (String)eventSource.getUserData();
            BreadcrumbObject bcToAdd = new BreadcrumbObject(currentView, target.getText());
            breadcrumbs.add(bcToAdd);
            System.out.println("Breadcrumbs should display: " + breadcrumbs);
        }
        return createBreadcrumbs();
    }

    /**
     * Adds and existing breadcrumbObject to breadcrumb list
     * @param bcObj breadcrumbObject to be added to breadcrumb list
     * @return updated list of breadcrumbs
     */
    public ObservableList<Button> add(BreadcrumbObject bcObj) {

        currentView = bcObj.getButtonInfo()[0];
        this.breadcrumbs.add(bcObj);
        return createBreadcrumbs();
    }

    /**
     * Extracts relevant breadcrumb information from a MenuItem event source
     * @param event MenuItem click that triggered the event
     * @return updated list of breadcrumbs
     */
    public ObservableList<Button> addMenu(ActionEvent event) {

        MenuItem eventSource = (MenuItem) event.getSource();
        if (!Objects.equals(eventSource.getUserData(), currentView)) {
            currentView = (String)eventSource.getUserData();
            breadcrumbs.add(new BreadcrumbObject(currentView, eventSource.getText()));
        }
        return createBreadcrumbs();
    }

    /**
     * Clears the breadcrumbs
     */
    public void clear() {

        breadcrumbButtons.clear();
        breadcrumbs.clear();
    }

    public String getCurrentView() {
        return currentView;
    }
}
