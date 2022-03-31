package r8.model.task;

import r8.model.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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

	@ManyToOne(fetch = FetchType.LAZY)
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

	@ManyToMany(cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE
	})
	@JoinTable(
			name = "account_task",
			joinColumns = @JoinColumn(name = "task_id"),
			inverseJoinColumns = @JoinColumn(name = "account_id"))
	private Set<Account> accounts;

	@ManyToMany(mappedBy = "tasks")
	private Set<Sprint> sprints;

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	@ManyToMany(cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE
	})
	@JoinTable(
			name = "task_team",
			joinColumns = @JoinColumn(name = "task_id"),
			inverseJoinColumns = @JoinColumn(name = "team_id"))
	private Set<Team> teams;

	/**
	 * Constructor
	 * @param n Task's name
	 */
	public Task(String n, TaskState ts, TaskType tt, float hours, String desc) {
		this.name = n;
		this.taskState = ts;
		this.taskStateString = taskState.toString();
		this.taskType = tt;
		this.hours = hours;
		this.description = desc;
		this.startDate = LocalDate.now();
	}

	public Task() {}

	public void assignToTeam(Team team) {
		teams.add(team);
		team.getTasks().add(this);
	}

	public void removeFromTeam(Team team) {
		teams.remove(team);
		team.getTasks().remove(this);
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Set<Team> getTeams() {
		return teams;
	}

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}

	public String toString(){
		return "TASK: " + this.name + ", " + this.description;
	}
}
