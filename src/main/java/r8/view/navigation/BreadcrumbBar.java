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

//TODO add history
public class BreadcrumbBar {

    private List<BreadcrumbObject> breadcrumbs = new ArrayList<>();
    private ObservableList<Button> breadcrumbButtons = FXCollections.observableArrayList();

    private String currentView;

    public ObservableList<Button> createBreadcrumbs () {

        for (BreadcrumbObject bcObj : breadcrumbs) {
            String[] buttonInfo = bcObj.getButtonInfo();
            Button bcButton = new Button(buttonInfo[1]);
            bcButton.setUserData(buttonInfo[0]);

            bcButton.setOnAction(event -> {
                try {
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

    public ObservableList<Button> add(BreadcrumbObject bcObj) {

        currentView = bcObj.getButtonInfo()[0];
        this.breadcrumbs.add(bcObj);
        return createBreadcrumbs();
    }

    public ObservableList<Button> addMenu(ActionEvent event) {

        MenuItem eventSource = (MenuItem) event.getSource();
        if (!Objects.equals(eventSource.getUserData(), currentView)) {
            currentView = (String)eventSource.getUserData();
            breadcrumbs.add(new BreadcrumbObject(currentView, eventSource.getText()));
        }
        return createBreadcrumbs();
    }

    public void clear() {

        breadcrumbButtons.clear();
        breadcrumbs.clear();
    }

    public String getCurrentView() {
        return currentView;
    }
}
