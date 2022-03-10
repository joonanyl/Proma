package r8.model;

import org.hibernate.annotations.Type;
import r8.model.task.Task;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author sanku, Joona Nylander
 *
 */


/* direct association w/ */
/* EVENT, TASK */

/* association w/ PROJECT */
@Entity
@Table(name = "account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@Type(type = "boolean")
	@Column(name="admin")
	private Boolean admin;

	@ManyToMany(mappedBy = "accounts")
	private Set<Project> projects = new HashSet<>();

	@ManyToMany(mappedBy = "accounts")
	private Set<Team> teams = new HashSet<>();

	@ManyToMany(mappedBy = "accounts")
	private Set<Task> tasks = new HashSet<>();

	public Account(String fName, String lName, String email, String password) {
		this.firstName = fName;
		this.lastName = lName;
		this.email = email;
		this.admin = false;
		this.password = AuthService.hashPassword(password);
	}

	public void removeFromProject(Project project) {
		projects.remove(project);
		project.getAccounts().remove(this);
	}

	public Account() {}

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

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public String toString() {
		return accountId + " " + firstName + " " + lastName + " " + email;
	}
}
