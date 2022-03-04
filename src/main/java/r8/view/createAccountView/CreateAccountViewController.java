package r8.view.createAccountView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;
import r8.model.Account;
import r8.model.TextFieldValidator;
import r8.view.navigation.GlobalControllerReference;

import java.io.IOException;

public class CreateAccountViewController {

    @FXML
    private TextField textFieldEmail;

    @FXML
    private TextField textFieldConfirmEmail;

    @FXML
    private TextField textFieldPassword;

    @FXML
    private TextField textFieldFirstName;

    @FXML
    private TextField textFieldLastName;

    @FXML
    private void navigate(ActionEvent event) throws IOException {
        GlobalControllerReference.getInstance().getLoginViewController().handleNavigation(event);
    }

    private String emailRegEx = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private String passwordRegEx = "([A-Za-z0-9]{5,30})";
    private String nameRegEx = "([A-Za-z0-9]{1,30})";

    public void initialize(){
        TextFieldValidator textFieldValidator = new TextFieldValidator();

        textFieldValidator.setValidation(textFieldEmail, emailRegEx);
        textFieldValidator.setValidation(textFieldPassword, passwordRegEx);
        textFieldValidator.setValidation(textFieldFirstName, nameRegEx);
        textFieldValidator.setValidation(textFieldLastName, nameRegEx);
        compareEmail();
    }


    private void compareEmail(){
        textFieldConfirmEmail.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                compare();
            }
        });
        compare();
    }
    private void compare(){
        if(!textFieldConfirmEmail.getText().equals(textFieldEmail.getText())){
            textFieldConfirmEmail.setStyle("-fx-border-color: red;");
        }else{
            textFieldConfirmEmail.setStyle("-fx-border-color: null;");
        }
    }

    @FXML
    private void createAccount(ActionEvent event) throws IOException {
        if(!textFieldEmail.getText().equals(textFieldConfirmEmail.getText())){
            System.out.println("email comparison");
            return;
        }
        if(!textFieldEmail.getText().matches(emailRegEx)){
            System.out.println("Email exception");
            return;
        }
        if(!textFieldPassword.getText().matches(passwordRegEx)){
            System.out.println("Password Exception");
            return;
        }
        if(!textFieldFirstName.getText().matches(nameRegEx) || !textFieldLastName.getText().matches(nameRegEx)){
            System.out.println("Name Exception");
            return;
        }
        Account account = new Account(textFieldFirstName.getText(), textFieldLastName.getText(),"000", textFieldEmail.getText());
        System.out.println(account.getFirstName());
        navigate(event);
    }
}
