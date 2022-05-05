package r8.model;

import org.hibernate.annotations.Type;
import r8.model.task.Task;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author sanku, Joona Nylander
 *
 */

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

	@ManyToMany(mappedBy = "accounts", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	private Set<Project> projects = new HashSet<>();

	@ManyToMany(mappedBy = "accounts", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	private Set<Team> teams = new HashSet<>();

	@ManyToMany(mappedBy = "accounts", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
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

	public Account(String firstName, String lastName, String email, String password, boolean admin) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = AuthService.hashPassword(password);
		this.admin = admin;
	}

	public Account() {}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	/**
	 *
	 * @return Returns a Set of projects the Account is assigned to.
	 */
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

	/**
	 *
	 * @return Returns a Set of Teams the Account is a part of.
	 */
	public Set<Team> getTeams() {
		return teams;
	}

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}

	/**
	 *
	 * @return Returns a Set of Tasks assigned to the Account
	 */
	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTask(Task t){
		this.tasks.add(t);
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	/**
	 *
	 * @return Returns the hashed password of the user
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Hashes the password String and assigns it to the user.
	 * @param password
	 */
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
		return this.accountId == account.accountId;
	}

	@Override
	public int hashCode(){
		return accountId;
	}
}
