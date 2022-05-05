package r8.view.mainView.profileView;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import r8.controller.Controller;
import r8.controller.IControllerAccount;
import r8.model.Account;
import r8.model.appState.AppState;
import r8.model.appState.IAppStateMain;
import r8.util.lang.LanguageHandler;
import r8.util.lang.ResourceHandler;

/**
 * @author Sebastian Lundin, , Aarni Pesonen, Teemu Tallskog
 */
public class ProfileViewController {

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
    private Button changePassword;

    @FXML
    private VBox changePasswordBox;

    @FXML
    private PasswordField oldPasswordTF;

    @FXML
    private PasswordField newPasswordTF;

    @FXML
    private PasswordField confirmNewPasswordTF;

    @FXML
    private Label characterCheck;

    @FXML
    private Label uppercaseCheck;

    @FXML
    private Label numberCheck;

    @FXML
    private Label profileInfoLabel;

    private ResourceHandler textLoader = ResourceHandler.getInstance();
    private final String passwordRegEx = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";

    /**
     * Initializes profile subview. Retrieves account information from {@link AppState}
     */
    @FXML
    private void initialize() {
        ResourceHandler loader = ResourceHandler.getInstance();
        if (controllerAccount.getAccount() != null) {
            addPasswordComparisonListener();
            hidePasswordChange();
            checkBoxIsAdmin.setSelected(adminAccount.getIsAdmin());
            checkBoxEnableTooltips.setSelected(adminAccount.getTooltipsEnabled());
            labelUserFirstNameDisplay.setText(controllerAccount.getAccount().getFirstName());
            labelUserLastNameDisplay.setText(controllerAccount.getAccount().getLastName());
            labelUserEmailDisplay.setText(controllerAccount.getAccount().getEmail());
        }

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

    /**
     * Sets user {@link Account} admin status.
     * Only available in development builds.
     */
    @FXML
    private void setAdminChecked() {
        if (controllerAccount.getAccount() != null)
            adminAccount.setIsAdmin(adminAccount.getIsAdmin());
    }

    /**
     * Sets user tooltip preferences
     */
    @FXML
    private void setTooltipChecked() {
        if (controllerAccount.getAccount() != null)
            adminAccount.setTooltipsEnabled(adminAccount.getTooltipsEnabled());
    }

    /**
     * Sets selected UI display language and reloads the parent main view
     */
    @FXML
    private void setSelectedLanguage() {
        LanguageHandler.changeLanguage(
                comboBoxUILanguage.getItems().get(comboBoxUILanguage.getSelectionModel().getSelectedIndex()));
                AppState.getInstance().getViewController().getApp().reloadMainView();
    }

    /**
     * Reveals password change related UI elements
     */
    @FXML
    private void showPasswordChange(){
        changePassword.setVisible(false);
        changePasswordBox.setVisible(true);
        changePasswordBox.setManaged(true);
    }

    /**
     * Hides password change related UI elements
     */
    @FXML
    private void hidePasswordChange(){
        changePassword.setVisible(true);
        changePasswordBox.setVisible(false);
        changePasswordBox.setManaged(false);
    }

    /**
     * Attemps to change user account password
     */
    @FXML
    private void changePassword(){
        Account account = AppState.INSTANCE.getLoggedAccount();
        if(checkPasswordInputs()){
            controllerAccount.updateAccount(account.getFirstName(), account.getLastName(),account.getEmail(), newPasswordTF.getText());
            showNotification(textLoader.getTextResource("passwordChangeSuccess"), "", true);
            hidePasswordChange();
        }
    }

    /**
     * Checks if user input password data matches required criteria
     * @return boolean based on input matching requested criteria
     */
    private boolean checkPasswordInputs(){
        IControllerAccount controllerLogin = new Controller();
        if(!controllerLogin.authenticatePassword(oldPasswordTF.getText())){
            showNotification(textLoader.getTextResource("oldPasswordInvalid"), textLoader.getTextResource("oldPasswordInvalidInfo"), false);
            return false;
        }
        if(!newPasswordTF.getText().equals(confirmNewPasswordTF.getText())){
            showNotification(textLoader.getTextResource("dontMatch"), textLoader.getTextResource("passwordMismatch"), false);
            return false;
        }
        if(!newPasswordTF.getText().matches(passwordRegEx)){
            showNotification(textLoader.getTextResource("invalidPassword"), textLoader.getTextResource("invalidPasswordInfo"), false);
            return false;
        }
        return true;
    }

    /**
     * Displays notification info based on password change success status
     * @param title of notification
     * @param text content of notification
     * @param success of password change action as boolean
     */
    private void showNotification(String title, String text, boolean success){
        ImageView icon = null;
        if(success) icon = new ImageView(new Image("/image/success-ico.png"));
        else icon = new ImageView(new Image("/image/fail-ico.png"));
        Notifications notification = Notifications.create()
                .title(title)
                .text(text)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT)
                .graphic(icon)
                .darkStyle()
                .owner(profileInfoLabel.getScene().getWindow());
        notification.show();
    }

    /**
     * Adds listeners password input and confirmation fields to and calls following compare method
     * when changes to the fields are made
     */
    private void addPasswordComparisonListener(){
        newPasswordTF.textProperty().addListener((observable, oldValue, newValue) -> compare());
        confirmNewPasswordTF.textProperty().addListener((observable, oldValue, newValue) -> compare());
        compare();
    }

    /**
     * Checks if user password input matches the required criteria
     */
    private void compare(){
        if(!newPasswordTF.getText().matches(passwordRegEx)){
            newPasswordTF.setStyle("-fx-border-color: red;");
        }else{
            newPasswordTF.setStyle("-fx-border-color: null;");
        }
        if(!confirmNewPasswordTF.getText().equals(newPasswordTF.getText())){
            confirmNewPasswordTF.setStyle("-fx-border-color: red;");
        }else{
            confirmNewPasswordTF.setStyle("-fx-border-color: null;");
        }
        //checks if password contains 8-30 characters
        if(!newPasswordTF.getText().matches(".{8,30}")){
            characterCheck.setTextFill(Color.web("#ff0000"));
        }else{
            characterCheck.setTextFill(Color.web("#21a300"));
        }
        //checks if password contains an uppercase letter
        if(!newPasswordTF.getText().matches("^(?=.*[a-z])(?=.*[A-Z]).*$")){
            uppercaseCheck.setTextFill(Color.web("#ff0000"));
        }else{
            uppercaseCheck.setTextFill(Color.web("#21a300"));
        }
        //checks if the password contains a number
        if(!newPasswordTF.getText().matches(".*[0-9].*")){
            numberCheck.setTextFill(Color.web("#ff0000"));
        }else{
            numberCheck.setTextFill(Color.web("#21a300"));
        }
    }
}
