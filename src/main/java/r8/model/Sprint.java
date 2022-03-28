package r8.model;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * 
 * @author sanku
 *
 */
/* direct association w/ */
/* TASK */
@Entity
@Table(name = "sprint")
public class Sprint {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sprint_id")
	private int sprintId;
	@Column(name = "name")
	private String name;
	@Column(name = "start_date")
	private LocalDate startDate;
	@Column(name = "end_date")
	private LocalDate endDate;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	private Project project;
	//OneToMany to Task?
	
	/**
	 * Constructor
	 * @param n Sprint's name
	 */
	public Sprint(String n, LocalDate sD, LocalDate eD, Project project) {
		this.name = n;
		this.startDate = sD;
		this.endDate = eD;
		this.project = project;
	}

	public Sprint() {}

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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return this.name + " " + this.startDate + " " + this.endDate + " ";
	}
}
