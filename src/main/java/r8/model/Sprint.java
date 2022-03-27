package r8.model;

import r8.model.task.Task;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

	@ManyToMany(cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE
	})
	@JoinTable(
			name = "sprint_task",
			joinColumns = @JoinColumn(name = "sprint_id"),
			inverseJoinColumns = @JoinColumn(name = "task_id")
	)
	private Set<Task> tasks = new HashSet<>();

	/**
	 * Constructor
	 * @param n Sprint's name
	 */
	public Sprint(String n, LocalDate sD, LocalDate eD, int projectId) {
		this.name = n;
		this.startDate = sD;
		this.endDate = eD;
	}

	public Sprint() {}

	public void addTask(Task task) {
		tasks.add(task);
		task.getSprints().add(this);
	}

	public void removeTask(Task task) {
		tasks.remove(task);
		task.getSprints().remove(this);
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

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public String toString() {
		return this.name + " " + this.startDate + " " + this.endDate + " ";
	}
}
