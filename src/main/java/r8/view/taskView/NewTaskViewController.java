package r8.view.taskView;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
// import org.controlsfx.control.textfield.TextFields;
import r8.model.Account;
import r8.model.Team;

import javax.swing.*;
import java.util.LinkedList;

public class NewTaskViewController {

    @FXML
    private ComboBox<Account> comboBoxUser;

    @FXML
    private ComboBox<Team> comboBoxTeam;


    public void initialize(){
        Account acc1 = new Account("Teemu", "Tallskog", "000", "email");
        Account acc2 = new Account("Teemua", "Tallskoga", "0001", "email");
        Account acc3 = new Account("Teemub", "Tallskogb", "0002", "email");

        comboBoxUser.getItems().add(acc1);
        comboBoxUser.getItems().add(acc2);
        comboBoxUser.getItems().add(acc3);

        comboBoxTeam.setEditable(true);
        comboBoxUser.setEditable(true);

      //  TextFields.bindAutoCompletion((TextField) comboBoxTeam.getEditor(), comboBoxTeam.getItems());
    //    TextFields.bindAutoCompletion((TextField) comboBoxUser.getEditor(), comboBoxUser.getItems());

    }
}
