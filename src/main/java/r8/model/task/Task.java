package r8.model.task;

import r8.model.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author sanku, Joona Nylander
 *
 */

/* direct association w/  */
/* COMMENT, tasktype, taskstate*/
@Entity
@Table(name = "task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_id")
	private int taskId;

	@Column(name = "name")
	private String name;

	@Transient
	private TaskState taskState;

	@Column(name = "state")
	private String taskStateString;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "task_type_id")
	private TaskType taskType;

	@Column(name = "description")
	private String description;

	@Column(name = "hours")
	private float hours;

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	@ManyToMany(
			fetch = FetchType.EAGER,
			cascade = {
			CascadeType.MERGE
	})
	@JoinTable(
			name = "account_task",
			joinColumns = @JoinColumn(name = "task_id"),
			inverseJoinColumns = @JoinColumn(name = "account_id"))
	private Set<Account> accounts = new HashSet<>();

	@ManyToMany(mappedBy = "tasks", fetch = FetchType.EAGER)
	private Set<Sprint> sprints = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	@ManyToMany(
			fetch = FetchType.EAGER,
			cascade = {
			CascadeType.MERGE
	})
	@JoinTable(
			name = "task_team",
			joinColumns = @JoinColumn(name = "task_id"),
			inverseJoinColumns = @JoinColumn(name = "team_id"))
	private Set<Team> teams = new HashSet<>();

	@OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Event> events = new HashSet<>();

	/**
	 * Constructor
	 * @param name Task's name
	 */
	public Task(String name, TaskState taskState, TaskType taskType, float hours, String desc) {
		this.name = name;
		this.taskState = taskState;
		this.taskStateString = this.taskState.toString();
		this.taskType = taskType;
		this.hours = hours;
		this.description = desc;
		this.startDate = LocalDate.now();
	}

	/**
	 * Constructor for user generated non-task related work events,
	 * to be saved to database as tasks
	 * @param name of work event
	 * @param desc description of work event
	 */
	public Task(String name, String desc) {
		this.name = name;
		this.description = desc;
	}

	public Task() {}

	public void addTeam(Team team) {
		teams.add(team);
		team.getTasks().add(this);
	}

	public void removeTeam(Team team) {
		teams.remove(team);
		team.getTasks().remove(this);
	}

	public void removeTeamWithId(int id) {
		for (Team t : teams) {
			if (t.getTeamId() == id) {
				teams.remove(t);
				t.getTasks().remove(this);
			}
		}
	}

	public void addAccount(Account account) {
		accounts.add(account);
		account.getTasks().add(this);
	}

	public void removeAccount(Account account) {
		accounts.remove(account);
		account.getTasks().remove(this);
	}

	public void removeAccountWithId(int id) {
		for (Account a : accounts) {
			if (a.getAccountId() == id) {
				accounts.remove(a);
				a.getProjects().remove(this);
			}
		}
	}

	public void addEvent(Event event) {
		events.add(event);
		event.setTask(this);
	}

	public void removeEvent(Event event) {
		events.remove(event);
		event.setTask(null);
	}

	public void removeEventWithId(int id) {
		for (Event e : events) {
			if (e.getEventId() == id) {
				events.remove(e);
				e.setTask(null);
			}
		}
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getName() {
		return name;
	}

	public Sprint getActiveSprint() {
		if (sprints != null) {
			for (Sprint sprint : sprints) {
				if (sprint.getEndDate().isAfter(LocalDate.now()) && sprint.getStartDate().isBefore(LocalDate.now()))
					System.out.println("Returning sprint " +sprint);
				return sprint;
			}
		}
		return null;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TaskState getTaskState() {
		return taskState;
	}

	public void setTaskState(TaskState taskState) {
		this.taskState = taskState;
	}

	public String getTaskStateString() {
		return taskStateString;
	}

	public void setTaskStateString(String taskStateString) {
		this.taskStateString = taskStateString;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getHours() {
		return hours;
	}

	public void setHours(float hours) {
		this.hours = hours;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	/**
	 *
	 * @return Returns a Set of Accounts assigned to the Task.
	 */
	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public Set<Sprint> getSprints() {
		return sprints;
	}

	public void setSprints(Set<Sprint> sprints) {
		this.sprints = sprints;
	}

	/**
	 *
	 * @return Returns the Project that the Task is assigned to.
	 */
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 *
	 * @return Returns a Set of Teams assigned to the Task.
	 */
	public Set<Team> getTeams() {
		return teams;
	}

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}

	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	public String toString(){
		return this.name + " - " + this.description;
	}
}
