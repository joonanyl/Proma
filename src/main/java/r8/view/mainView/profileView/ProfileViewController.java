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
    
    private final String passwordRegEx = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";

    // Retrieves loggedAccount data from AppState
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

    @FXML
    private void setAdminChecked() {
        if (controllerAccount.getAccount() != null)
            adminAccount.setIsAdmin(adminAccount.getIsAdmin());
    }

    @FXML
    private void setTooltipChecked() {
        if (controllerAccount.getAccount() != null)
            adminAccount.setTooltipsEnabled(adminAccount.getTooltipsEnabled());
    }

    @FXML
    private void setSelectedLanguage() {
        LanguageHandler.changeLanguage(
                comboBoxUILanguage.getItems().get(comboBoxUILanguage.getSelectionModel().getSelectedIndex()));
                AppState.getInstance().getViewController().getApp().reloadMainView();
    }

    @FXML
    private void showPasswordChange(){
        changePassword.setVisible(false);
        changePasswordBox.setVisible(true);
        changePasswordBox.setManaged(true);
    }

    @FXML
    private void hidePasswordChange(){
        changePassword.setVisible(true);
        changePasswordBox.setVisible(false);
        changePasswordBox.setManaged(false);
    }

    @FXML
    private void changePassword(){
        Account account = AppState.INSTANCE.getLoggedAccount();
        if(checkPasswordInputs()){
            controllerAccount.updateAccount(account.getFirstName(), account.getLastName(),account.getEmail(), newPasswordTF.getText());
            showNotification(LanguageHandler.getText("passwordChangeSuccess"), "", true);
            hidePasswordChange();
        }
    }

    private boolean checkPasswordInputs(){
        IControllerAccount controllerLogin = new Controller();
        if(!controllerLogin.authenticatePassword(oldPasswordTF.getText())){
            showNotification(LanguageHandler.getText("oldPasswordInvalid"), LanguageHandler.getText("oldPasswordInvalidInfo"), false);
            return false;
        }
        if(!newPasswordTF.getText().equals(confirmNewPasswordTF.getText())){
            showNotification(LanguageHandler.getText("dontMatch"), LanguageHandler.getText("passwordMismatch"), false);
            return false;
        }
        if(!newPasswordTF.getText().matches(passwordRegEx)){
            showNotification(LanguageHandler.getText("invalidPassword"), LanguageHandler.getText("invalidPasswordInfo"), false);
            return false;
        }
        return true;
    }

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

    private void addPasswordComparisonListener(){
        newPasswordTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                compare();
            }
        });
        confirmNewPasswordTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                compare();
            }
        });
        compare();
    }
    //compares the new passwords and changes the CSS of the input elements to give the user feedback
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
