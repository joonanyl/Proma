package r8.model;

import r8.model.task.Task;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author sanku, Joona Nylander
 *
 */

/* direct association w/ */
/* TEAM, SPRINT, TASK */


/* association w/ ACCOUNT */
@Entity
@Table(name = "project")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "project_id")
	private int projectId;

	@Column(name = "name")
	private String name;

	@ManyToMany(mappedBy = "projects")
	private Set<Account> accounts;

	@Column(name = "description")
	private String description;

	@Transient
	private ArrayList<Task> tasks;

	/**
	 * Contructor
	 * @param name Project's name
	 */
	public Project(String name, String description) {
		this.name = name;
		this.description = description;
		this.accounts = new HashSet<>();
	}

	public Project() {}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<Task> getTasks() {
		return tasks;
	}

	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}

}
