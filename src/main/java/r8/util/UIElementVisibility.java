package r8.util;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import r8.model.appState.AppState;

/**
 * @author Aarni Pesonen
 */
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

    public void setTooltipVisibility(Button tooltip) {
        tooltip.setVisible(AppState.INSTANCE.getTooltipsEnabled());
        tooltip.setManaged(AppState.INSTANCE.getTooltipsEnabled());
    }

    public void setTooltipVisibility(Button[] tooltips) {
        for (Button tooltip : tooltips) {
            tooltip.setVisible(AppState.INSTANCE.getTooltipsEnabled());
            tooltip.setManaged(AppState.INSTANCE.getTooltipsEnabled());
        }
    }
}
