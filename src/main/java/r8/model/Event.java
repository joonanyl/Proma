package r8.model;

import r8.model.task.Task;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * 
 * @author sanku
 *
 */


/* direct association w/ */
/* TASK */
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private int eventId;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "hours")
    private float hours;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public Event(String desc, LocalDate date, float hours, Account account) {
        this.description = desc;
        this.date = date;
        this.hours = hours;
        this.account = account;
    }

    public Event(String description, LocalDate date, float hours, Account account, Task task) {
        this.description = description;
        this.date = date;
        this.hours = hours;
        this.account = account;
        this.task = task;
    }

    public Event() {}

    public Account getAccount() {
        return account;
    }

    public int getEventId() {
        return eventId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public float getHours() {
        return hours;
    }

    public String getHoursString() { return Float.toString(hours); }

    public Task getTask() {
        return task;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setHours(float hours) {
        this.hours = hours;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "Event " + this.eventId + ": " + this.date + " " + this.description + " associated with task "
                + this.task + " created by " + this.account;
    }
}
