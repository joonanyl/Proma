package r8;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application
{
	/* 
	 * sannabranch mergattu mainiin
	 */
	
    @FXML
    private BorderPane mainViewPane;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(App.class.getResource("/fxml/login-view.fxml")));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Proma - Login");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}