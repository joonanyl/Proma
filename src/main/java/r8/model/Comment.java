package r8.model;

import javax.persistence.*;
import java.util.LinkedList;

/**
 * 
 * @author sanku, joonanyl
 *
 */

/*
 * kelasin et hashmap<comment, linkedlist<comment>> -mapillä pystyis ehkä tulostamaan koko puun.
 *  
 */

@Table(name = "comment")
public class Comment {
	//authorID or account?? date/time?
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int comment_id;

	@Column(name = "author_id")
	private int authorID;

	@Column(name = "task_id")
	private int taskID;

	@Column(name = "parent_comment_id")
	private int parentCommentID;

	@Column(name = "title")
	private String title;

	/**
	 * Kommentti merkkijonona
	 */
	@Column(name = "content")
	private String content;
	
	private LinkedList<Comment> childComments = null;
	private Account author;
	
	
	/**
	 * Constructor
	 * @param a Author
	 * @param t Comment's text
	 */
	public Comment(Account a, String t) {
		this.author = a;
		this.authorID = a.getAccountID();

		this.content = t;
	}

	public Comment() {}
	
	/**
	 * Muokkaa kommenttia
	 * @param newText uusi teksti
	 */
	public void editText(String newText) {
		this.content = newText;
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
		return content + " // author: " + author;
	}
}
