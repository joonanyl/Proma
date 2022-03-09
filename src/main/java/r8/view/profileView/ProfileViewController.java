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
        if (appStateMain.getAccount() != null){
            checkBoxIsAdmin.setSelected(appStateMain.getIsAdmin());
            labelUserFirstNameDisplay.setText(appStateMain.getAccount().getFirstName());
            labelUserLastNameDisplay.setText(appStateMain.getAccount().getLastName());
            labelUserEmailDisplay.setText(appStateMain.getAccount().getEmail());
        }
        labelUserPhoneDisplay.setText("not set");
    }

    @FXML
    private void setAdminChecked() {
        if (appStateMain.getAccount() != null)
        appStateMain.setIsAdmin(appStateMain.getIsAdmin());
    }
}
