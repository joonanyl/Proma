package r8.model;

import r8.model.task.Task;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author sanku
 * @author Joona Nylander
 *
 */

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

	@ManyToOne
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

	public Sprint(String name, LocalDate startDate, LocalDate endDate, Project project) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.project = project;
	}

	public Sprint(String name, LocalDate startDate, LocalDate endDate) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Sprint(String name) {
		this.name = name;
	}

	public Sprint() {}

	/**
	 * Adds a task to the sprint
	 * @param task
	 */
	public void addTask(Task task) {
		tasks.add(task);
		task.getSprints().add(this);
	}
	/**
	 * Removes a task from the sprint
	 * @param task
	 */
	public void removeTask(Task task) {
		tasks.remove(task);
		task.getSprints().remove(this);
	}

	/**
	 * Removes a task from the sprint by the task's id.
	 * @param id
	 */
	public void removeTaskWithId(int id) {
		for (Task t : tasks) {
			if (t.getTaskId() == id) {
				tasks.remove(t);
				t.getSprints().remove(this);
			}
		}
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
		return this.name + " " + this.startDate + " - " + this.endDate;
	}

}
