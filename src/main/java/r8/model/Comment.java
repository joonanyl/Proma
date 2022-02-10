package r8.model;

import java.util.LinkedList;

/**
 * 
 * @author sanku
 *
 */

/*
 * kelasin et hashmap<comment, linkedlist<comment>> -mapillä pystyis ehkä tulostamaan koko puun.
 *  
 */
public class Comment {
	//authorID or account?? date/time?
	private int commentID; // pitäskö olla static?
	
	private Account author; 
	
	/**
	 * Kommentti merkkijonona
	 */
	private String commentText;
	
	private LinkedList<Comment> childComments = null;
	
	
	/**
	 * Constructor
	 * @param a Author
	 * @param t Comment's text
	 */
	public Comment(Account a, String t) {
		this.author = a;	
		this.commentText = t;
	}
	
	/**
	 * Muokkaa kommenttia
	 * @param newText uusi teksti
	 */
	public void editText(String newText) {
		this.commentText = newText;
	}
	
	public void addReply(Comment reply) {
		if(childComments == null)
			this.childComments = new LinkedList<Comment>();
		
		childComments.add(reply);
	}
	
	public String printChildComments() {
		String ret;
		if(childComments != null) {
			ret = this.toString() + " " + "\n \t";
			int order = 1;
			for(Comment c : childComments) {
				ret += order + ": " + c.toString() + "; \n \t";
				order++;
			}
		} else {
			ret = "No replys to show";
		}
		return ret;
	}
	
	public String toString() {
		return commentText + " // author: " + author;
	}
}
