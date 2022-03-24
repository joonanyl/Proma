package r8;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import r8.controller.Controller;
import r8.controller.IControllerLogin;
import r8.model.appState.AppState;
import r8.view.IViewController;
import r8.view.loginView.LoginViewController;
import r8.view.mainView.MainViewController;
import r8.view.navigation.GetView;

import java.io.IOException;
import java.util.Objects;

public class App extends Application
{
	private Stage stage;

    private String viewToLoad;
    private boolean displayLogin;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        displayLogin = true;
        switchScene();
    }

    public void switchScene() {
        try {
            if (displayLogin) { viewToLoad = "login-view"; }
            if (!displayLogin) { viewToLoad = "main-view"; }
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/" + viewToLoad + ".fxml")));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            setStageSize();

            // pass App reference to controller to enable sceneSwitching
            IViewController viewController = loader.getController();
            viewController.setApp(this);

            AppState.getInstance().setViewController(viewController);
            displayLogin = !displayLogin;

            stage.show();
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private void setStageSize() {
        stage.setTitle("Proma - Project Manager v0.1");
        stage.setResizable(!displayLogin);
        stage.setMaximized(!displayLogin);

        if (displayLogin) {
            stage.setTitle("Proma - Login");
            stage.sizeToScene();
            stage.centerOnScreen();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}