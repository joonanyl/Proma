package r8.controller;

import javafx.event.ActionEvent;
import r8.view.IViewController;

public interface IControllerMain {
    
    IViewController getActiveViewController();

    void setActiveViewController(IViewController viewController);

}
