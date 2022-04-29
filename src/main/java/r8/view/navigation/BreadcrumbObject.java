package r8.view.navigation;

/**

 * @author Aarni Pesonen
 *
 */

/**
 * Stores navigation information for breadcrumbs
 */
public class BreadcrumbObject {

    /**
     * String variables are stored here in order: userData, viewType, viewName
     * userData stores view resource name without .fxml, added to each generated button
     * viewType is displayed first on breadcrumb button
     * optional: name displayed on breadcrumb button after viewType
     */
    private final String[] buttonInfo;

    public BreadcrumbObject(String userData, String viewType) {
        buttonInfo = new String[]{userData, viewType};
    }

    public BreadcrumbObject(String userData, String viewType, String viewName) {
        buttonInfo = new String[]{userData, viewType, viewName};
    }

    /**
     * @return breadcrumb button information
     */
    public String[] getButtonInfo() {
        return buttonInfo;
    }
}
