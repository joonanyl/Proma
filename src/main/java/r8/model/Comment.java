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

	private int commentId;

	@Column(name = "author_id")
	private int authorID;

	@Column(name = "task_id")
	private int taskID;

	@Column(name = "parent_comment_id")
	private int parentCommentID;

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
	 * @param content Comment's text
	 */
	public Comment(Account a, String content) {
		this.author = a;
		this.authorID = a.getAccountId();
		this.content = content;
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

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "comment_id")
	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	// Pitää muistaa muuttaa tämä tietokannasta authorid:ksi
	@Column(name = "account_id")
	public int getAuthorID() {
		return authorID;
	}

	public void setAuthorID(int authorID) {
		this.authorID = authorID;
	}

	public int getTaskID() {
		return taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	public int getParentCommentID() {
		return parentCommentID;
	}

	public void setParentCommentID(int parentCommentID) {
		this.parentCommentID = parentCommentID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LinkedList<Comment> getChildComments() {
		return childComments;
	}

	public void setChildComments(LinkedList<Comment> childComments) {
		this.childComments = childComments;
	}

	public Account getAuthor() {
		return author;
	}

	public void setAuthor(Account author) {
		this.author = author;
	}

	public String toString() {
		return content + " // author: " + author;
	}
}
