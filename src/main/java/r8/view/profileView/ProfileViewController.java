package r8.view.profileView;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateMain;

public class ProfileViewController {

    IAppStateMain appStateMain = AppState.getInstance();

    @FXML
    private CheckBox checkBoxEnableTooltips;

    @FXML
    private CheckBox checkBoxIsAdmin;

    @FXML
    private ComboBox<?> comboBoxUILanguage;

    @FXML
    private ComboBox<?> comboBoxUiTheme;

    @FXML
    private VBox containerProfile;

    @FXML
    private Label labelUserEmailDisplay;

    @FXML
    private Label labelUserFirstNameDisplay;

    @FXML
    private Label labelUserLastNameDisplay;

    @FXML
    private Label labelUserPhoneDisplay;

    public void initialize() {
        checkBoxIsAdmin.setSelected(appStateMain.getIsAdmin());
    }

    @FXML
    private void setAdminChecked() {
        appStateMain.setIsAdmin(appStateMain.getIsAdmin());
    }
}
