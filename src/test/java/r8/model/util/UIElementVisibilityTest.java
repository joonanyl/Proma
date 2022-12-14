package r8.model.util;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.*;
import r8.model.Account;
import r8.util.UIElementVisibility;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UIElementVisibilityTest {

    static UIElementVisibility visibility;
    static Pane pane;
    static Button tooltip;

    @BeforeAll
    static void setUpBeforeTesting() {
            visibility = new UIElementVisibility();
            pane = new Pane();
            //tooltip = new Button();
    }

    @Test
    @Order(1)
    void toggleOff() {

        visibility.toggleOff(pane);

        assertFalse(pane.isVisible(), "Setting UI element visibility off failed.");
        assertFalse(pane.isManaged(), "Setting UI element visibility off failed.");
    }

    @Test
    @Order(2)
    void toggleOn() {

        visibility.toggleOn(pane);

        assertTrue(pane.isVisible(), "Setting UI element visibility on failed.");
        assertTrue(pane.isManaged(), "Setting UI element visibility on failed.");
    }

    @Test
    @Order(3)
    void toggleVisibility() {

        //toggle visibility off
        visibility.toggleVisibility(pane);
        //toggle visibility back on
        visibility.toggleVisibility(pane);

        assertTrue(pane.isVisible(), "Toggle between UI visibility states failed.");
        assertTrue(pane.isManaged(), "Toggle between UI managed states failed.");
    }

    @Test
    @Order(4)
    void toggleAdminVisibilityOn() {

        Account account = new Account();
        account.setAdmin(true);

        visibility.toggleAdminVisibility(pane, account.getAdmin());

        assertTrue(pane.isVisible(), "UI elemenet visibility toggle based on admin status returns a wrong value.");
        assertTrue(pane.isManaged(), "UI elemenet management toggle based on admin status returns a wrong value.");
    }

    @Test
    @Order(5)
    void toggleAdminVisibilityOff() {

        Account account = new Account();
        account.setAdmin(false);

        visibility.toggleAdminVisibility(pane, account.getAdmin());

        assertFalse(pane.isVisible(), "UI element visibility toggle based on admin status returns a wrong value.");
        assertFalse(pane.isManaged(), "UI element management toggle based on admin status returns a wrong value.");
    }
/*
    @Test
    @Order(6)
    void setTooltipVisibility() {

        visibility.setTooltipVisibility(tooltip);

        assertTrue(tooltip.isVisible(), "UI element visibility toggle based on preset boolean returns a wrong value.");
        assertTrue(tooltip.isManaged(), "UI element management toggle based on preset boolean returns a wrong value.");
    }

    @Test
    @Order(7)
    void toggleAdminVisibilityWithArray() {

        Button tooltip = new Button();
        Button tooltip2 = new Button();
        Button[] tooltips = {tooltip, tooltip2};

        visibility.setTooltipVisibility(tooltips);

        for (Button button : tooltips) {
            assertTrue(button.isVisible(), "UI element visibility toggle based on preset boolean returns a wrong value.");
        }
    }*/
}

