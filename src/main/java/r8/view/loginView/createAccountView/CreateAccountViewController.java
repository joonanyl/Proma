package r8.view.loginView.createAccountView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import r8.controller.Controller;
import r8.controller.IControllerLogin;
import r8.model.Account;
import r8.model.TextFieldValidator;
import r8.util.TextLoader;

import java.io.IOException;

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

    public void initialize(){
        controller = new Controller();

        TextFieldValidator textFieldValidator = new TextFieldValidator();

        textFieldValidator.setValidation(textFieldEmail, emailRegEx);
        textFieldValidator.setValidation(textFieldFirstName, nameRegEx);
        textFieldValidator.setValidation(textFieldLastName, nameRegEx);
        comparePassword();
    }

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        controller.getActiveViewController().handleNavigation(event);
    }

    private void comparePassword(){
        passwordField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                compare();
            }
        });
        confirmPasswordField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                compare();
            }
        });
        compare();
    }
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

    @FXML
    private void createAccount(ActionEvent event) throws IOException {
        TextLoader textloader = TextLoader.getInstance();
        if(!passwordField.getText().equals(confirmPasswordField.getText())){
            //System.out.println("Password comparison");
            showAlert(textloader.getResource("dontMatch"), textloader.getResource("passwordMissmatch"));
            return;
        }
        if(!textFieldEmail.getText().matches(emailRegEx)){
            //System.out.println("Email exception");
            showAlert(textloader.getResource("invalidEmail"), textloader.getResource("invalidEmailInfo"));
            return;
        }
        if(!passwordField.getText().matches(passwordRegEx)){
            //System.out.println("Password Exception");
            showAlert(textloader.getResource("invalidPassword"), textloader.getResource("invalidPasswordInfo"));
            return;
        }
        if(!textFieldFirstName.getText().matches(nameRegEx) || !textFieldLastName.getText().matches(nameRegEx)){
            //System.out.println("Name Exception");
            showAlert(textloader.getResource("invalidName"), textloader.getResource("invalidNameInfo"));
            return;
        }
        Account account = new Account(textFieldFirstName.getText(), textFieldLastName.getText(), textFieldEmail.getText(), passwordField.getText());
        System.out.println(account.getFirstName());
        showAlert(textloader.getResource("success"), textloader.getResource("accountSuccess"));

        controller.createAccount(textFieldFirstName.getText(), textFieldLastName.getText(), textFieldEmail.getText(), passwordField.getText());
        navigate(event);
    }

    private void showAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);

        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }
}
