package r8.model;

import r8.model.task.Task;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author sanku, Joona Nylander
 *
 */

@Entity
@Table(name = "project")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "project_id")
	private int projectId;

	@Column(name = "name")
	private String name;

	@ManyToMany
	@JoinTable(
			name = "project_account",
			joinColumns = @JoinColumn(name = "project_id"),
			inverseJoinColumns = @JoinColumn(name = "account_id"))
	private Set<Account> accounts = new HashSet<>();

	@Column(name = "description")
	private String description;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
	private List<Task> tasks = new ArrayList<>();

	@OneToMany(mappedBy = "project")
	private List<Team> teams = new ArrayList<>();

	/**
	 * Contructor
	 * @param name Project's name
	 */
	public Project(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public void addAccount(Account account) {
		this.accounts.add(account);
		account.getProjects().remove(this);
	}

	public void removeAccount(Account account) {
		this.accounts.remove(account);
		account.getProjects().remove(this);
	}

	public void addTask(Task task) {
		this.tasks.add(task);
		task.setProject(this);
	}

	public void removeTask(Task task) {
		this.tasks.remove(task);
		task.setProject(null);
	}

	public void addTeam(Team team) {
		this.teams.add(team);
		team.setProject(this);
	}

	public void removeProject(Team team) {
		this.teams.remove(team);
		team.setProject(null);
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

	public List<Task> getTasks() {
		return tasks;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public String toString() {
		return this.projectId + " " + this.name;
	}
}
