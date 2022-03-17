package r8.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import r8.model.Account;
import r8.view.IViewController;

import java.util.List;

public interface IControllerMain {
    
    IViewController getActiveViewController();

    void setActiveViewController(IViewController viewController);

    List<Account> getAllAccounts();

    void createProject(String name, String description, ObservableList<Account> accountList, ObservableList<String> teamList);

}
