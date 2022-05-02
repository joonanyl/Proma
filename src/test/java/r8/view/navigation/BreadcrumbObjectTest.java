package r8.view.navigation;

import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BreadcrumbObjectTest {

    static String userData;
    static String viewType;
    static String viewName;

    @BeforeAll
    static void setUpBeforeTesting() {
        userData = "main-view";
        viewType = "viewType";
        viewName = "Main View";
    }

    @Test
    @Order(1)
    void getButtonInfo() {

        String[] comparison = new String[]{userData, viewType, viewName};

        BreadcrumbObject breadcrumbObject = new BreadcrumbObject(userData, viewType, viewName);
        String[] buttonInfo = breadcrumbObject.getButtonInfo();

        assertEquals(breadcrumbObject.getButtonInfo()[0], comparison[0], "getButtonInfo does not return correct userData.");
        assertEquals(breadcrumbObject.getButtonInfo()[1], comparison[1], "getButtonInfo does not return correct viewType.");
        assertEquals(breadcrumbObject.getButtonInfo()[2], comparison[2], "getButtonInfo does not return correct viewName.");

    }

    @Test
    @Order(2)
    void createBcObjWith2Params() {

        BreadcrumbObject breadcrumbObject = new BreadcrumbObject(userData, viewType, viewName);
        String[] buttonInfo = breadcrumbObject.getButtonInfo();
        assertEquals(buttonInfo[0], userData, "Breadcrumb userDate does not match.");
        assertEquals(buttonInfo[1], viewType, "Breadcrumb view type is incorrect.");
        assertEquals(buttonInfo[2], viewName, "Breadcrumb view name is incorrect.");

    }

    @Test
    @Order(3)
    void createBcObjWith3Params() {

        BreadcrumbObject breadcrumbObject = new BreadcrumbObject(userData, viewType, viewName);
        String[] buttonInfo = breadcrumbObject.getButtonInfo();
        assertEquals(buttonInfo[0], userData, "Breadcrumb userDate does not match.");
        assertEquals(buttonInfo[1], viewType, "Breadcrumb view type is incorrect.");
        assertEquals(buttonInfo[2], viewName, "Breadcrumb view name is incorrect.");

    }
}
