package r8.model;

import r8.model.task.Task;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author sanku
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

	@Transient
	private Set<Team> teams;

	@Column(name = "budget")
	private String budget;

	@Column(name = "description")
	private String description;

	@Transient
	private ArrayList<Task> tasks;

	public Project(String pn, String description, String budget) {
		this.name = pn;
		this.budget = budget;
		this.description = description;
		this.accounts = new HashSet<>();
		this.teams = new HashSet<>();
	}

	public Project() {}


	public void addTeam(Team t) {
		if(!this.teams.contains(t)){
			this.teams.add(t);
			t.setProject(this);
		}
	}

	public Set<Team> getTeamsList(){
		return this.teams;
	}

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

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
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

	public Set<Team> getTeams() {
		return teams;
	}

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}

}
