package r8.model;

import r8.model.task.Task;

import javax.persistence.*;
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

	@ManyToMany(cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE })
	@JoinTable(
			name = "project_account",
			joinColumns = @JoinColumn(name = "project_id"),
			inverseJoinColumns = @JoinColumn(name = "account_id"))
	private Set<Account> accounts;

	@Column(name = "description")
	private String description;

	@Transient
	private ArrayList<Task> tasks;

	@OneToMany(mappedBy = "project")
	private List<Team> teams;

	/**
	 * Contructor
	 * @param name Project's name
	 */
	public Project(String name, String description) {
		this.name = name;
		this.description = description;
		this.accounts = new HashSet<>();
	}

	public void addAccount(Account account) {
		accounts.add(account);
		account.getProjects().add(this);
	}

	public void removeAccount(Account account) {
		accounts.remove(account);
		account.getProjects().remove(this);
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
