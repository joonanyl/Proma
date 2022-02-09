package r8.model;

/**
 * Luokka, joka kuvaa työryhmiä
 * 
 * @author sanku
 *
 */


/* direct association w/ */
/* ACCOUNT, TASK */

public class Team {

	/**
	 * tiimin nimi
	 */
	private String teamName;

	/**
	 * Constructor
	 * @param tn name
	 */
	public Team(String tn) {
		this.teamName = tn;
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
	
}
