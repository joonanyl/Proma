package r8.view.navigation;

import r8.model.task.Task;

/**
 * Stores navigation information for breadcrumbs
 * @author Aarni Pesonen
 */
public class BreadcrumbObject {

    /**
     * String variables are stored here in order: userData, viewType, viewName
     * userData stores view resource name without .fxml, added to each generated button
     * viewType is displayed first on breadcrumb button
     * optional: name displayed on breadcrumb button after viewType, (for ex. Project: TestProject1)
     */
    private final String[] buttonInfo;

    /**
     * Constructs a new breadcrumbObject using provided data
     * @param userData stores view loading information
     * @param viewType contains a string to be displayed on a breadcrumb button
     */
    public BreadcrumbObject(String userData, String viewType) {
        buttonInfo = new String[]{userData, viewType};
    }

    /**
     * Alternate constructor with the added viewName.
     * @param userData stores view loading information
     * @param viewType contains a string to be displayed on a breadcrumb button
     * @param viewName stores subview specific name info such as {@link Task} name
     */
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
