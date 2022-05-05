package r8.model;

import r8.model.task.Task;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author sanku, Joona Nylander
 *
 */

@Entity
@Table(name = "team")
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "team_id")
	private int teamId;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	private Project project;

	@Column(name = "name")
	private String teamName;

	@ManyToMany(
			fetch = FetchType.EAGER,
			cascade = {
			CascadeType.PERSIST
	})
	@JoinTable(
			name = "account_team",
			joinColumns = @JoinColumn(name = "team_id"),
			inverseJoinColumns = @JoinColumn(name = "account_id")
	)
	private Set<Account> accounts = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "teams")
	private Set<Task> tasks = new HashSet<>();

	public Team(String teamName, Project project) {
		this.teamName = teamName;
		this.project = project;
	}
	public Team(String teamName){
		this.teamName = teamName;
	}

	public Team() {}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	/**
	 * Adds an account to the team.
	 * @param account
	 */
	public void addAccount(Account account) {
		// tästä testi että ei olisi duplikaattijäseniä
		System.out.println(this.accounts);
		if(!accounts.contains(account)) {
			accounts.add(account);
			account.getTeams().add(this);
		}
	}

	/**
	 * Removes an account from the team.
	 * @param account
	 */
	public void removeAccount(Account account) {
		accounts.remove(account);
		account.getTeams().remove(this);
	}

	/**
	 * Removes an account from the team by the account's id.
	 * @param id
	 */
	public void removeAccountById(int id) {
		for (Account a : accounts) {
			if (a.getAccountId() == id) {
				accounts.remove(a);
				a.getTeams().remove(this);
			}
		}
	}
	
	/**
	 *
	 * @return Returns a Set of accounts that are part of the team
	 */
	public Set<Account> getAccounts(){
		return this.accounts;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	public String toString() { return this.teamName; }
	
}
