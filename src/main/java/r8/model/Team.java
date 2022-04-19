package r8.model;

import r8.model.task.Task;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Luokka, joka kuvaa työryhmiä
 * 
 * @author sanku
 *
 */

/*
 * oisko tiimeillekin enum?
 */

/* direct association w/ */
/* ACCOUNT, TASK */
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
			CascadeType.PERSIST,
			CascadeType.MERGE
	})
	@JoinTable(
			name = "account_team",
			joinColumns = @JoinColumn(name = "team_id"),
			inverseJoinColumns = @JoinColumn(name = "account_id")
	)
	private Set<Account> accounts = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "teams")
	private Set<Task> tasks = new HashSet<>();

	/**
	 * Constructor
	 * @param tn name
	 */
	public Team(String tn, Project project) {
		this.teamName = tn;
		this.project = project;
	}

	public Team() {}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	/**
	 * palauttaa tiimin nimen
	 * @return tiimin nimi
	 */
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	/**
	 * Lisää jäsen
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

	public void removeAccount(Account account) {
		accounts.remove(account);
		account.getTeams().remove(this);
	}
	
	/**
	 *
	 * @return account
	 */
	public Set<Account> getAccounts(){
		return this.accounts;
	}

	// Seuraavasta kahdesta metodista tulee DAO-versiot tietokannan avulla
	public Account getMemberById(int id) {
		for (Account a: accounts) {
			if (a.getAccountId() == id)
				return a;
		}
		return null;
	}

	public Account getAccountByName(String fName, String lName) {
		for (Account a: accounts) {
			if (a.getFirstName().equals(fName) && a.getLastName().equals(lName))
				return a;
		}
		return null;
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

	public String toString() { return this.teamId + " " + this.teamName; }
	
}
