package r8;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import r8.view.loginView.LoginViewController;
import r8.view.mainView.MainViewController;
import r8.view.navigation.GlobalControllerReference;

import java.io.IOException;
import java.util.Objects;

public class App extends Application
{
	private Stage stage;
    private Parent root;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        switchToLoginScene();
    }

    public void switchToLoginScene() {
        try{
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/login-view.fxml")));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Proma - Login");
            stage.setScene(scene);
            stage.setResizable(false);

            LoginViewController controller = loader.getController();
            controller.setApp(this);

            GlobalControllerReference.getInstance().setLoginViewController(controller);

            System.out.println("controller App: " + controller.getApp());

            stage.show();

        } catch (IOException | IllegalArgumentException e) {
           e.printStackTrace();
        }
    }

    public void switchToWorkScene() {
        try{
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/main-view.fxml")));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Proma - Project Manager v0.1");
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("Press ESC to exit fullscreen mode.");

            MainViewController controller = loader.getController();
            controller.setApp(this);

            GlobalControllerReference.getInstance().setMainViewController(controller);

            stage.show();

        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}