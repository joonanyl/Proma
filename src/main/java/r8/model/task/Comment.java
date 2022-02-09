package r8.model.task;

/**
 * 
 * @author sanku
 *
 */

public class Comment {
//authorID or account??
	private int authorID, commentID; // pitäskö olla static?
	
	/**
	 * Constructor
	 * @param aID AuthorID
	 */
	public Comment(int aID) {
		this.authorID = aID;
	}
	
}
