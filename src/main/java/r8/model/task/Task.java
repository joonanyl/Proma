package r8.model.task;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * @author sanku
 *
 */

/* direct association w/  */
/* COMMENT, tasktype, taskstate*/
public class Task {

	private String title;

	
	/**
	 * Constructor
	 * @param n Task's name
	 */
	public Task(String n) {
		this.title = n;
	}
	
}
