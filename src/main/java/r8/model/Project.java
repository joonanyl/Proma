package r8.model;

import r8.model.task.Task;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
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

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(
			name = "project_account",
			joinColumns = @JoinColumn(name = "project_id"),
			inverseJoinColumns = @JoinColumn(name = "account_id"))
	private Set<Account> accounts = new HashSet<>();

	@Column(name = "description")
	private String description;

	// Removing a project removes all tasks it
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Task> tasks = new HashSet<>();
	// Removing a project removes all teams under
	@OneToMany(mappedBy = "project", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	private Set<Team> teams = new HashSet<>();

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Sprint> sprints = new HashSet<>();

	/**
	 * Contructor
	 * @param name Project's name
	 */
	public Project(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public Project() {}

	/**
	 * Adds an account to the project.
	 * @param account
	 */
	public void addAccount(Account account) {
		accounts.add(account);
		account.getProjects().remove(this);
	}

	/**
	 * Removes an account from the project
	 * @param account
	 */
	public void removeAccount(Account account) {
		accounts.remove(account);
		account.getProjects().remove(this);
	}

	/**
	 * Adds a task to the project
	 * @param task
	 */
	public void addTask(Task task) {
		tasks.add(task);
		task.setProject(this);
	}
	/**
	 * Removes a task from the project
	 * @param task
	 */
	public void removeTask(Task task) {
		tasks.remove(task);
		task.setProject(null);
	}

	/**
	 * Adds a team to the project
	 * @param team
	 */
	public void addTeam(Team team) {
		teams.add(team);
		team.setProject(this);
	}
	/**
	 * Removes a team from the project
	 * @param team
	 */
	public void removeTeam(Team team) {
		teams.remove(team);
		team.setProject(null);
	}
	/**
	 * Adds a sprint to the project
	 * @param sprint
	 */
	public void addSprint(Sprint sprint) {
		sprints.add(sprint);
		sprint.setProject(this);
	}
	/**
	 * Removes a sprint from the project
	 * @param sprint
	 */
	public void removeSprint(Sprint sprint) {
		sprints.remove(sprint);
		sprint.setProject(null);
	}

	/**
	 * Removes an account from the project by account's id
	 * @param id
	 */
	public void removeAccountWithId(int id) {
		for (Account a : accounts) {
			if (a.getAccountId() == id) {
				accounts.remove(a);
				a.getProjects().remove(this);
			}
		}
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
		if (name != null)
			return name;

		return "";
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

	public Set<Task> getTasks() {
		return tasks;
	}

	public Set<Team> getTeams() {
		return teams;
	}

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	public Set<Sprint> getSprints() {
		return sprints;
	}

	/**
	 *
	 * @return Returns a sprint from the project that is currently going on.
	 * @see Sprint
	 */
	public Sprint getActiveSprint() {
		if (sprints != null) {
			for (Sprint sprint : sprints) {
				if (sprint.getEndDate().isAfter(LocalDate.now()) && sprint.getStartDate().isBefore(LocalDate.now()))
					System.out.println("Active sprint is " +sprint);
				return sprint;
			}
		}
		return null;
	}

	public void setSprints(Set<Sprint> sprints) {
		this.sprints = sprints;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Project project = (Project) o;

		return projectId == project.projectId;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
