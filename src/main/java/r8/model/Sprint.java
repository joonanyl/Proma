package r8.model;

import java.util.Date;

/**
 * 
 * @author sanku
 *
 */
/* direct association w/ */
/* TASK */
public class Sprint {

	private int sprintId;
	private String name;
	// startDate & endDate ?
	private Date startDate;
	private Date endDate;
	private int projectId;

	
	/**
	 * Constructor
	 * @param n Sprint's name
	 */
	public Sprint(String n, Date sD, Date eD, int projectId) {
		this.name = n;
		this.startDate = sD;
		this.endDate = eD;
		this.projectId = projectId;
	}

	public int getSprintId() {
		return sprintId;
	}

	public void setSprintId(int sprintId) {
		this.sprintId = sprintId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	@Override
	public String toString() {
		return this.name + " " + this.startDate + " " + this.endDate + " " + "projectID: " + this.projectId;
	}
}
