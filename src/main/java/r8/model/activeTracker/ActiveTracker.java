package r8.model.activeTracker;

import r8.model.Event;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public enum ActiveTracker {

    INSTANCE;
    Event event;
    long startTime;
    long endTime;
    boolean trackingActive = false;

    ActiveTracker() {}

    public static ActiveTracker getInstance()  { return INSTANCE; }

    public void startTracking(Event event) {
        trackingActive = true;
        this.event = event;
        startTime = System.nanoTime();
    }

    public Event stopTracking() {
        trackingActive = false;
        endTime = System.nanoTime();
        long result = (TimeUnit.MINUTES.convert(endTime-startTime, TimeUnit.NANOSECONDS));
        event.setDate(LocalDate.now());
        return event;
    }

    public boolean isActive() {
        return this.trackingActive;
    }
}
