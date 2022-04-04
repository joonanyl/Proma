package r8.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author sanku
 *
 */

/*
 * kelasin et hashmap<comment, linkedlist<comment>> -mapillä pystyis ehkä tulostamaan koko puun.
 *  
 */
@Entity
@Table(name = "comment")
public class Comment {
	//authorID or account?? date/time?

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private int commentId;

	@Column(name = "task_id")
	private int taskID;

	@JoinColumn(name = "parent_comment_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Comment parentComment;

	/**
	 * Kommentti merkkijonona
	 */
	@Column(name = "content")
	private String content;
	// Kenties tämä hakisi tietokannasta kommentit, jossa parent_comment_id == comment_id
	@Transient
	private Set<Comment> childComments = new HashSet<>();
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	private Account account;


	/**
	 * Constructor
	 * @param a Author
	 * @param content Comment's text
	 */
	public Comment(Account a, String content) {
		this.account = a;
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
			this.childComments = new HashSet<Comment>();

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

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getTaskID() {
		return taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	public Comment getParentComment() {
		return parentComment;
	}

	public void setParentComment(Comment parentComment) {
		this.parentComment = parentComment;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<Comment> getChildComments() {
		return childComments;
	}

	public void setChildComments(Set<Comment> childComments) {
		this.childComments = childComments;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String toString() {
		return content + " // author: " + account;
	}
}
