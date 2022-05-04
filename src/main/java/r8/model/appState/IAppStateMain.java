package r8.model.appState;


import r8.model.*;
import r8.model.task.Task;

/**
 * Interface for {@link AppState} used in conjunction with applications main work view
 * Only contains {@link AppState} methods appropriate for main views operations
 * @author Aarni Pesonen
 */
public interface IAppStateMain {

    /**
     * Return {@link Account} user has logged in with
     * @return user {@link Account}
     */
    Account getAccount();

    /**
     * Return user {@link Account} admin status
     * @return admin status as a boolean
     */
    boolean getIsAdmin();

    /**
     * Sets user {@link Account} admin status
     * Only available in development builds
     * @param isAdmin as a boolean value
     */
    void setIsAdmin(boolean isAdmin);

    /**
     * Return user tooltip preference information
     * @return user tooltip preference as a boolean value
     */
    boolean getTooltipsEnabled();

    /**
     * Sets user tooltip preference
     * Can be set from profile view
     * @param tooltipsEnabled boolean to set the value at
     */
    void setTooltipsEnabled(boolean tooltipsEnabled);

    /**
     * Returns currently active {@link Task}
     * Used in {@link Task} specific subview navigation
     * @return currently active {@link Task}
     */
    Task getSelectedTask();

    /**
     * Sets currently active {@link Task}
     * @param task currently active
     */
    void setSelectedTask(Task task);

    /**
     * Returns currently active {@link Project}
     * Used in {@link Project} specific subview navigation
     * @return currently active {@link Project}
     */
    Project getSelectedProject();

    /**
     * Sets currently active {@link Project}
     * @param project currently active
     */
    void setSelectedProject(Project project);
}
