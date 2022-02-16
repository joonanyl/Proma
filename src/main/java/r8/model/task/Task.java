package r8.model.task;

import r8.model.Account;
import r8.model.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * 
 * @author sanku
 *
 */

/* direct association w/  */
/* COMMENT, tasktype, taskstate*/
@Entity
@Table(name = "task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int taskId;
	private String name;
	private TaskState ts;
	private TaskType tt;
	private String description;
	private float hours;
	private Date startDate;
	private Date endDate;
	@Transient
	private ArrayList<Account> assignedAccounts;
	private int sprintId;
	private int projectId;
	private int teamId;
	
	/**
	 * Constructor
	 * @param n Task's name
	 */
	public Task(String n, TaskState ts, TaskType tt) {
		this.name = n;
		this.ts = ts;
		this.tt = tt;
	}

	public Task() {}

	public void assignToTeam(int id) {
		this.teamId = id;
	}

	public void assignToProject(int id) {
		this.teamId = id;
	}

	public void assignToSprint(int id) {
		this.teamId = id;
	}

	public Comment createComment(Account a, String comment) {
		return new Comment(a, comment);
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

	public TaskState getTs() {
		return ts;
	}

	public void setTs(TaskState ts) {
		this.ts = ts;
	}

	public TaskType getTt() {
		return tt;
	}

	public void setTt(TaskType tt) {
		this.tt = tt;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public ArrayList<Account> getAssignedAccounts() {
		return assignedAccounts;
	}

	public void setAssignedAccounts(ArrayList<Account> assignedAccounts) {
		this.assignedAccounts = assignedAccounts;
	}

	public String toString() {
		return this.name + " " + this.tt + " " + this.ts;
	}
}
