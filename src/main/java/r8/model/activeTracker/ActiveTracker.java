package r8.model.activeTracker;

import r8.model.Event;
import r8.model.task.Task;
import r8.view.mainView.dashboardView.DashboardViewController;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;
/**
 * With active tracker user can track time spend working on a selected {@link Task}
 * User work {@link Event} is passed in as a parameter and when working finishes it is returned
 * @author Aarni Pesonen
 */
public enum ActiveTracker {

    INSTANCE;
    Event event;
    long startTime;
    long endTime;
    boolean trackingActive = false;

    ActiveTracker() {}

    /**
     * Returns enum singleton instance
     * @return singleton instance
     */
    public static ActiveTracker getInstance()  { return INSTANCE; }

    /**
     * Starts tracking of selected {@link Event} work time information
     * Records work start time as System.nanoTime()
     * @param event to track
     */
    public void startTracking(Event event) {
        trackingActive = true;
        this.event = event;
        startTime = System.nanoTime();
    }

    /**
     * Stops tracking of previously selected work {@link Event}
     * Records work end time as System.nanoTime() and saves information
     * in {@link Event} object
     * @return previously tracked {@link Event} object
     */
    public Event stopTracking() {
        trackingActive = false;
        endTime = System.nanoTime();
        long result = (TimeUnit.MINUTES.convert(endTime-startTime, TimeUnit.NANOSECONDS));
        event.setDate(LocalDate.now());
        return event;
    }

    /**
     * Returns active trackers activity state information
     * Used in {@link DashboardViewController} to determine
     * appropriate {@link ActiveTracker} function and button text
     * @return trackers activity status
     */
    public boolean isActive() {
        return this.trackingActive;
    }
}
