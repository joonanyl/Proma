package r8.model;

import r8.model.task.Task;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int projectId;
	private String name;

	@ManyToMany
	@JoinTable(name = "project_account", joinColumns = { @JoinColumn(name = "project_id") },
			inverseJoinColumns = { @JoinColumn(name = "account_id") })
	private Set<Account> accounts = new HashSet<Account>();

	@OneToMany
	private Set<Team> teams = new HashSet<Team>();

	@Column(name = "budget")
	private String budget;

	@Column(name = "description")
	private String description;
	// linkedlist tasks?
	@Transient
	private ArrayList<Task> tasks;

	/**
	 * Contructor
	 * @param pn Project's name
	 */
	public Project(String pn, String budget) {
		this.name = pn;
		this.budget = budget;
	}

	public Project() {}
	
	public void addTeam(Team t) {
		this.teams.add(t);
	}
	
	public Set<Team> getTeamsList(){
		return this.teams;
	}
	
	// del a team
	public void removeTeam(int teamId) {
		this.teams.remove(teamId);
		System.out.println("Team " + teamId + " removed");
	}
	
	
	public String printTeamsList() {
		String ret = "No Teams assigned to " + this.name + " yet";
		if(teams.size()>0) {
			ret = "Teams in " + this.name + ": \n \t ";

			for(Team t : teams) {
				ret += t.getTeamName() + ": " + t.toString() + " \n \t ";
			}
		}
		return ret;
	}


	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
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
}
