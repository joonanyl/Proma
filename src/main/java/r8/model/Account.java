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

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "password")
	private String password;

	@Type(type = "boolean")
	@Column(name="admin")
	private Boolean admin;

	@ManyToMany(mappedBy = "accounts", fetch = FetchType.EAGER)
	private Set<Project> projects = new HashSet<>();

	@ManyToMany(mappedBy = "accounts", fetch = FetchType.EAGER)
	private Set<Team> teams = new HashSet<>();

	@ManyToMany(mappedBy = "accounts", fetch = FetchType.EAGER)
	private Set<Task> tasks = new HashSet<>();

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Event> events = new HashSet<>();

	@Transient
	private boolean tooltipsEnabled = true;

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
		this.tasks.add(t);
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = AuthService.hashPassword(password);
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

	public Boolean getTooltipsEnabled() { return this.tooltipsEnabled; }

	public void setTooltipsEnabled(Boolean tooltipsEnabled) {this.tooltipsEnabled = tooltipsEnabled; }

	public String toString() {
		return firstName + " " + lastName;
	}

	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	@Override
	public boolean equals(Object object){
		if(object == null){
			return false;
		}
		if(object.getClass() != this.getClass()){
			return false;
		}
		final Account account = (Account) object;
		if(this.accountId != account.accountId){
			return false;
		}
		if(!this.email.equals(account.email)){
			return false;
		}
		return true;
	}

	@Override
	public int hashCode(){
		return (int) accountId * email.hashCode() * firstName.hashCode() * lastName.hashCode();
	}
}
