package r8.model.util;

import javafx.scene.layout.Pane;

public class UIElementVisibility {

    public Pane toggleVisibility(Pane pane) {
        pane.setVisible(!pane.isVisible());
        pane.setManaged(!pane.isManaged());
        return pane;
    }
}
