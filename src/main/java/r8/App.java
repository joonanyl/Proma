package r8;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import r8.model.appState.AppState;
import r8.util.TextLoader;
import r8.view.IViewController;

import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;

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

            //ResourceBundle resourceBundle = TextLoader.getInstance().getBundle();

            FXMLLoader loader = new FXMLLoader();
            TextLoader textLoader = TextLoader.getInstance();
            ResourceBundle resourceBundle = TextLoader.getInstance().getBundle();
            loader.setLocation(Objects.requireNonNull(getClass().getResource("/fxml/" + viewToLoad + ".fxml")));
            loader.setResources(resourceBundle);

            //loader.setLocation(App.class.getResource("/fxml/" + viewToLoad + ".fxml"));
            //loader.setResources(resourceBundle);

            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(displayLogin ? textLoader.getResource("loginTitle") : textLoader.getResource("appTitle"));
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
