package r8.util;

import javafx.scene.layout.Pane;

public class UIElementVisibility {

    public void toggleOff(Pane pane) {
        pane.setVisible(false);
        pane.setManaged(false);
    }

    public void toggleOn(Pane pane) {
        pane.setVisible(true);
        pane.setManaged(true);
    }

    public void toggleVisibility(Pane pane) {
        pane.setVisible(!pane.isVisible());
        pane.setManaged(!pane.isManaged());
    }

    public void toggleAdminVisibility(Pane pane, boolean admin) {
        if(admin) { pane.setVisible(true); pane.setManaged(true); }
        if(!admin) { pane.setVisible(false); pane.setManaged(false); }
    }
}