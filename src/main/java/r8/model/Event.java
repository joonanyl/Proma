package r8.model;

import r8.model.task.Task;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @see Task
 * @author sanku, Joona Nylander
 *
 */

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

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

    public Event (String desc, Account account, Task task, Project project) {
        this.description = desc;
        this.account = account;
        this.task = task;
        this.project = project;
    }

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

    public Event(String description, LocalDate date, float hours, Account account, Task task, Project project) {
        this.description = description;
        this.date = date;
        this.hours = hours;
        this.account = account;
        this.task = task;
        this.project = project;
    }

    public Event(int eventId, String description, LocalDate date, float hours, Account account, Task task, Project project, Sprint sprint) {
        this.eventId = eventId;
        this.description = description;
        this.date = date;
        this.hours = hours;
        this.account = account;
        this.task = task;
        this.project = project;
        this.sprint = sprint;
    }

    public Event() {}

    public Account getAccount() {
        return account;
    }

    public int getEventId() {
        return eventId;
    }

    public LocalDate getDate() { return date; }

    /**
     * @param locale Localization setting
     * @return Returns a date formatted based on user's selected language option.
     */
    public String getFormattedDate(String locale) {
        if (locale.equals("fi_FI")) return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        else return date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    @Override
    public String toString() {
        return "Event " + this.eventId + ": " + this.date + " " + this.description + " associated with task "
                + this.task + " created by " + this.account;
    }
}
