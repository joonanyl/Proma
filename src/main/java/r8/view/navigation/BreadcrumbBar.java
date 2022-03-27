package r8.view.navigation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class BreadcrumbBar {

    private List<BreadcrumbObject> breadcrumbs = new ArrayList<>();
    private ObservableList<Button> breadcrumbButtons = FXCollections.observableArrayList();

    /*public void subViewNav(String currentView, String userData) {
        if (!userData.equals(currentView)) {
            System.out.println("Clicked " + userData);
            // No need to use NavigationHandler
            mainViewPane.setCenter(nav.handleNavigation(event));
            currentView = userData;
            breadcrumbs.add(new BreadcrumbObject(userData, viewType));
            System.out.println("Breadcrumbs should display: " + breadcrumbs);
            createBreadcrumbs();
        }
    }

    public void leftBarNav(String currentView, String userData) {
        if (!userData.equals(currentView)) {
            hBoxBreadcrumb.getChildren().clear();
            breadcrumbs.clear();
            breadcrumbButtons.clear();
            System.out.println("Clicked " + userData);
            mainViewPane.setCenter(nav.handleNavigation(event));
            currentView = userData;
            BreadcrumbObject test = new BreadcrumbObject(userData, viewType);
            breadcrumbs.add(test);
            System.out.println("Breadcrumbs should display: " + breadcrumbs);
            createBreadcrumbs();
        }
    }*/

    public ObservableList<Button> createBreadcrumbs () {

        for (BreadcrumbObject bcObj : breadcrumbs) {
            String[] buttonInfo = bcObj.getButtonInfo();
            Button bcButton = new Button(buttonInfo[1]);
            bcButton.setUserData(buttonInfo[0]);
            System.out.println("hbox buttons in list: " + breadcrumbButtons);
            breadcrumbButtons.add(bcButton);
            System.out.println("hbox buttons in list after adding: " + breadcrumbButtons);
        }
       return breadcrumbButtons;
    }

    public void add(BreadcrumbObject breadcrumbObject) {
        breadcrumbs.add(breadcrumbObject);
    }

    public void reset() {
        breadcrumbButtons.clear();
    }
}
