package r8.model.util;

import javafx.scene.layout.Pane;
import r8.model.Account;

public class UIElementVisibility {

    public Pane toggleVisibility(Pane pane) {
        pane.setVisible(!pane.isVisible());
        pane.setManaged(!pane.isManaged());
        return pane;
    }

    public Pane toggleAdminVisibility(Pane pane, boolean admin) {
        if(admin) { pane.setVisible(true); pane.setManaged(true); };
        if(!admin) { pane.setVisible(false); pane.setManaged(false); };
        return pane;
    }
}
