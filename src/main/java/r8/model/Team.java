package r8.model;

import java.util.LinkedList;

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

public class Team {

	/**
	 * tiimin nimi
	 */
	private String teamName;
	private LinkedList<Account> teamMembers;

	/**
	 * Constructor
	 * @param tn name
	 */
	public Team(String tn) {
		this.teamName = tn;
		this.teamMembers = new LinkedList<Account>();
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
	public void addMembers(LinkedList<Account> newMembers) {
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
	public LinkedList<Account> getMembers(){
		return this.teamMembers;
	}
	
	public String toString() {
		String ret = "";
		for(Account acc : teamMembers) {
			ret += acc.toString() + "; ";
		}
		return ret;
	}
	
}
