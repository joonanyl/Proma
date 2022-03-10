package r8.view.loginView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import r8.App;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateLogin;
import r8.view.navigation.GetView;
import r8.view.navigation.NavigationHandler;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class LoginViewController {

    private App app;
    final IAppStateLogin appStateLogin = AppState.getInstance();

    @FXML
    private BorderPane mainViewPane;

    public void initialize() {
       appStateLogin.setLoginViewController(this);
       initialTest();
    }

    public void navigationTest(ActionEvent event) throws IOException{
        final Node eventSource = (Node) event.getSource();
        String userData = (String) eventSource.getUserData();
        System.out.println("Clicked " + userData);
        GetView viewLoader = new GetView();
        Pane view = viewLoader.getView(userData);
        URL viewUrl = getClass().getResource("/fxml/" + userData + ".fxml");
        FXMLLoader loader = new FXMLLoader(viewUrl);

        mainViewPane.setCenter(view);
    }

    public void handleNavigation(ActionEvent event) throws IOException {
        NavigationHandler nav = new NavigationHandler();
        mainViewPane.setCenter(nav.handleNavigation(event));
    }

    public App getApp() {
        return this.app;
    }

    private void setInitialView() {

        appStateLogin.setLoginViewController(this);
        GetView viewLoader = new GetView();
        Pane view = viewLoader.getView("login-credentials-view");
        mainViewPane.setCenter(view);
    }

    private void initialTest() {
        System.out.print("App: " + app);
        try{
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/login-credentials-view.fxml")));
            Pane view = loader.load();

            LoginCredentialsViewController controller = loader.getController();
            controller.setApp(app);

            mainViewPane.setCenter(view);

        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void setApp(App app) {
        this.app = app;
    }
}
