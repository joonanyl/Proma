package r8.model;

import java.util.LinkedList;

/**
 * 
 * @author sanku
 *
 */

/* direct association w/ */
/* TEAM, SPRINT, TASK */


/* association w/ ACCOUNT */

public class Project {
	
	private String name;
	private LinkedList<Team> teams;
	// linkedlist tasks?
	
	
	
	/**
	 * Contructor
	 * @param pn Project's name
	 */
	public Project(String pn) {
		this.name = pn;
		this.teams = new LinkedList<Team>();
	}
	
	public void addTeam(Team t) {
		this.teams.add(t);
	}
	
	public LinkedList<Team> getTeamsList(){
		return this.teams;
	}
	
	// del a team
	
	
	
	public String printTeamsList() {
		String ret = "No Teams assigned to " + this.name + " yet";
		if(teams.size()>0) {
			ret = "Teams in " + this.name + ": \n \t ";

			for(Team t : teams) {
				ret += t.getTeamName() + ": " + t.toString() + " \n \t ";
			}
		}
		return ret;
	}
	
}
