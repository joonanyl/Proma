package r8.view.mainView.profileView;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import r8.controller.Controller;
import r8.controller.IControllerAccount;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateMain;
import r8.util.lang.LanguageHandler;
import r8.util.lang.ResourceHandler;

public class ProfileViewController {

    // TODO needs further refactoring
    IAppStateMain adminAccount = AppState.getInstance();
    IControllerAccount controllerAccount = new Controller();

    @FXML
    private CheckBox checkBoxEnableTooltips;

    @FXML
    private CheckBox checkBoxIsAdmin;

    @FXML
    private ComboBox<String> comboBoxUILanguage;

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

    // Retrieves loggedAccount data from AppState
    @FXML
    private void initialize() {
        ResourceHandler loader = ResourceHandler.getInstance();
        if (controllerAccount.getAccount() != null) {
            checkBoxIsAdmin.setSelected(adminAccount.getIsAdmin());
            labelUserFirstNameDisplay.setText(controllerAccount.getAccount().getFirstName());
            labelUserLastNameDisplay.setText(controllerAccount.getAccount().getLastName());
            labelUserEmailDisplay.setText(controllerAccount.getAccount().getEmail());
        }
        labelUserPhoneDisplay.setText(LanguageHandler.getText("notSet"));

        // Setting available languages listed in app properties file.
        try {
            String[] languages = loader.getAppResource("availableLanguages").split(":");
            String currentLang = loader.getAppResource("setLocale");
            for (String language : languages) {
                comboBoxUILanguage.getItems().add(language);
                if (language.equals(currentLang)) {
                    comboBoxUILanguage.getSelectionModel()
                            .select(comboBoxUILanguage.getItems().lastIndexOf(currentLang));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void setAdminChecked() {
        if (controllerAccount.getAccount() != null)
            adminAccount.setIsAdmin(adminAccount.getIsAdmin());
    }

    @FXML
    private void setSelectedLanguage() {
        LanguageHandler.changeLanguage(
                comboBoxUILanguage.getItems().get(comboBoxUILanguage.getSelectionModel().getSelectedIndex()));
    }
}
