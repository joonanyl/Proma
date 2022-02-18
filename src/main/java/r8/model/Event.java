package r8.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

/**
 * 
 * @author sanku
 *
 */


/* direct association w/ */
/* TASK */
public class Event {
		// date + hours

    private int eventId;
    private String description;
    private Date date;
    private float hours;
    private int accountId;
    private int taskId;

    public Event(String desc, Date date, float hours, int taskId, Account a) {
        this.description = desc;
        this.date = date;
        this.hours = hours;
        this.accountId = a.getAccountId();
        this.taskId = taskId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getHours() {
        return hours;
    }

    public void setHours(float hours) {
        this.hours = hours;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "Event " + this.eventId + ": " + this.date + " " + this.description + " associated with task "
                + this.taskId + " created by " + this.accountId;
    }
}
