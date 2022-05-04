package r8.view.loginView.createAccountView;
import r8.model.dao.AccountDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import r8.controller.Controller;
import r8.controller.IControllerLogin;
import r8.model.Account;
import r8.model.TextFieldValidator;
import r8.util.lang.ResourceHandler;

/**
 * Controller for the account creation subview
 * Can be loaded within loginView
 * @author Teemu Tallskog, Aarni Pesonen
 */
public class CreateAccountViewController {

    @FXML
    private TextField textFieldEmail;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField textFieldFirstName;

    @FXML
    private TextField textFieldLastName;

    @FXML
    private Label characterCheck;

    @FXML
    private Label uppercaseCheck;

    @FXML
    private Label numberCheck;

    private IControllerLogin controller;

    private String emailRegEx = "(?:[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private String passwordRegEx = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
    private String nameRegEx = "([A-Za-z0-9]{1,30})";

    /**
     * Initializes {@link Account} creation view
     */
    public void initialize(){
        controller = new Controller();

        TextFieldValidator.setValidation(textFieldEmail, emailRegEx);
        TextFieldValidator.setValidation(textFieldFirstName, nameRegEx);
        TextFieldValidator.setValidation(textFieldLastName, nameRegEx);
        comparePassword();
    }

    /**
     * Handles navigation based on received ActionEvent button userData
     * @param event triggering the navigation
     */
    @FXML
    private void navigate(ActionEvent event)  {
        controller.getActiveViewController().handleNavigation(event);
    }

    /**
     * Adds listeners password input and confirmation fields to and calls following compare method
     * when changes to the fields are made
     */
    private void comparePassword(){
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> compare());
        confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) -> compare());
        compare();
    }

    /**
     * Checks if user password input matches the required criteria
     */
    private void compare(){
        if(!passwordField.getText().matches(passwordRegEx)){
            passwordField.setStyle("-fx-border-color: red;");
        }else{
            passwordField.setStyle("-fx-border-color: null;");
        }
        if(!confirmPasswordField.getText().equals(passwordField.getText())){
            confirmPasswordField.setStyle("-fx-border-color: red;");
        }else{
            confirmPasswordField.setStyle("-fx-border-color: null;");
        }
        //checks if password contains 8-30 characters
        if(!passwordField.getText().matches(".{8,30}")){
            characterCheck.setTextFill(Color.web("#ff0000"));
        }else{
            characterCheck.setTextFill(Color.web("#21a300"));
        }
        //checks if password contains an uppercase letter
        if(!passwordField.getText().matches("^(?=.*[a-z])(?=.*[A-Z]).*$")){
            uppercaseCheck.setTextFill(Color.web("#ff0000"));
        }else{
            uppercaseCheck.setTextFill(Color.web("#21a300"));
        }
        //checks if the password contains a number
        if(!passwordField.getText().matches(".*[0-9].*")){
            numberCheck.setTextFill(Color.web("#ff0000"));
        }else{
            numberCheck.setTextFill(Color.web("#21a300"));
        }
    }

    /**
     * Checks if {@link Account} data input fields data is in correct format. If format is acceptable
     * new {@link Account} object is created with user provided data. Event data is then send to {@link AccountDAO}
     * for new {@link Account} creation and view is changed back to loginCredentialsView.
     * @param event triggering the creation action (Create {@link Account} button)
     */
    @FXML
    private void createAccount(ActionEvent event) {
        ResourceHandler textLoader = ResourceHandler.getInstance();
        if(!passwordField.getText().equals(confirmPasswordField.getText())){
            //System.out.println("Password comparison");
            showAlert(textLoader.getTextResource("dontMatch"), textLoader.getTextResource("passwordMismatch"));
            return;
        }
        if(!textFieldEmail.getText().matches(emailRegEx)){
            //System.out.println("Email exception");
            showAlert(textLoader.getTextResource("invalidEmail"), textLoader.getTextResource("invalidEmailInfo"));
            return;
        }
        if(!passwordField.getText().matches(passwordRegEx)){
            //System.out.println("Password Exception");
            showAlert(textLoader.getTextResource("invalidPassword"), textLoader.getTextResource("invalidPasswordInfo"));
            return;
        }
        if(!textFieldFirstName.getText().matches(nameRegEx) || !textFieldLastName.getText().matches(nameRegEx)){
            //System.out.println("Name Exception");
            showAlert(textLoader.getTextResource("invalidName"), textLoader.getTextResource("invalidNameInfo"));
            return;
        }
        Account account = new Account(textFieldFirstName.getText(), textFieldLastName.getText(), textFieldEmail.getText(), passwordField.getText());
        System.out.println(account.getFirstName());
        showAlert(textLoader.getTextResource("success"), textLoader.getTextResource("accountSuccess"));

        controller.createAccount(textFieldFirstName.getText(), textFieldLastName.getText(), textFieldEmail.getText(), passwordField.getText());
        navigate(event);
    }

    /**
     * Displays an alert with situation appropriate information for the user
     * @param title of the alert
     * @param text information to be displayed in alert window
     */
    private void showAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }
}
