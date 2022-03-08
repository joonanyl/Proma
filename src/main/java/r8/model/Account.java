package r8.model;

import r8.model.task.Task;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author sanku
 *
 */


/* direct association w/ */
/* EVENT, TASK */

/* association w/ PROJECT */
@Entity
@Table(name = "account")
public class Account {

	@Id
	@Column(name = "account_id")
	private int accountId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;
	/*
	@Column(name = "admin")
	private Boolean admin;
	*/
	@ManyToMany(cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE
	})
	@JoinTable(
			name = "project_account",
			joinColumns = @JoinColumn(name = "account_id"),
			inverseJoinColumns = @JoinColumn(name = "project_id"))
	private Set<Project> projects;

	@ManyToMany(mappedBy = "accounts")
	private Set<Team> teams;

	@ManyToMany(mappedBy = "accounts")
	private Set<Task> tasks;

	public Account(String fName, String lName, String email, String pw) {
		this.firstName = fName;
		this.lastName = lName;
		this.email = email;
		this.projects = new HashSet<>();
		this.teams = new HashSet<>();

		this.password = AuthService.hashPassword(pw);
	}

	public Account() {}

	public void assignToProject(Project project) {
		projects.add(project);
		project.getAccounts().add(this);
	}

	public void removeFromProject(Project project) {
		projects.remove(project);
		project.getAccounts().remove(this);
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Set<Team> getTeams() {
		return teams;
	}

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTask(Task t){
		if(!this.tasks.contains(t)) {
			this.tasks.add(t);
		}
	}

	public void setTasks(Set<Task> tasks) {
		// tähän pitää laittaa looppeja ettei tulis duplikaatteja
		this.tasks = tasks;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String toString() {
		return accountId + " " + firstName + " " + lastName;
	}
}
