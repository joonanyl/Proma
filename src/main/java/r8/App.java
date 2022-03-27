package r8;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import r8.model.appState.AppState;
import r8.view.IViewController;

import java.io.IOException;
import java.util.Objects;

public class App extends Application
{
	private Stage stage;
    private boolean displayLogin;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        displayLogin = true;
        switchScene();
    }

    public void switchScene() {
        try {
            String viewToLoad = displayLogin ? "login-view" : "main-view";
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/" + viewToLoad + ".fxml")));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(displayLogin ? "Proma - Login" : "Proma - Project Manager v0.2");
            setStageSize();

            // pass App reference to controller to enable sceneSwitching
            IViewController viewController = loader.getController();
            viewController.setApp(this);

            AppState.getInstance().setViewController(viewController);
            displayLogin = !displayLogin;

            stage.show();
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Failed to load view.");
        }
    }

    private void setStageSize() {
        stage.setResizable(!displayLogin);
        stage.setMaximized(!displayLogin);

        if (displayLogin) {
            stage.sizeToScene();
            stage.centerOnScreen();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}