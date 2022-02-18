package r8.model;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

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


	private int teamId;
	/**
	 * tiimin nimi
	 */
	private int projectId;
	private String teamName;

	@Transient
	private LinkedList<Account> teamMembers;

	/**
	 * Constructor
	 * @param tn name
	 */
	public Team(String tn) {
		this.teamName = tn;
		this.teamMembers = new LinkedList<Account>();
	}

	public Team() {}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "team_id")
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
	@Column(name = "name", nullable = false)
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	/**
	 * Lisää yksi (1) tiiminjäsenen 
	 * @param newMember tiimiin lisättävä tili
	 */
	public void addMember(Account newMember) {
		this.teamMembers.add(newMember);
	}
	
	/**
	 * Lisää monta jäsentä kerralla
	 * @param newMembers LinkedList uusista jäsenistä
	 */
	public void addMembers(List<Account> newMembers) {
		// tästä testi että ei olisi duplikaattijäseniä
		for(Account a : newMembers) {
			if(!teamMembers.contains(a))
				teamMembers.add(a);
		}		
	}
	
	/**
	 * Palauttaa listan tiiminjäsenistä
	 * @return lista tilejä, jotka kuuluvat kyseiseen tiimiin
	 */
	@Transient
	public LinkedList<Account> getMembers(){
		return this.teamMembers;
	}

	// Seuraavasta kahdesta metodista tulee DAO-versiot tietokannan avulla
	public Account getMemberById(int id) {
		for (Account a: teamMembers) {
			if (a.getAccountId() == id)
				return a;
		}
		return null;
	}

	public Account getMemberByName(String fName, String lName) {
		for (Account a: teamMembers) {
			if (a.getFirstName() == fName && a.getLastName() == lName)
				return a;
		}
		return null;
	}

	public String toString() {
		String ret = "";
		for(Account acc : teamMembers) {
			ret += acc.toString() + "; ";
		}
		return ret;
	}
	
}
