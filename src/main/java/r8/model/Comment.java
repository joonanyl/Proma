package r8.model;

import r8.model.task.Task;

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

	@ManyToOne
	@JoinColumn(name = "task_id")
	private Task task;

	@ManyToOne
	@JoinColumn(name = "parent_comment_id")
	private Comment parentComment;

	/**
	 * Kommentti merkkijonona
	 */
	@Column(name = "content")
	private String content;

	// Kenties tämä hakisi tietokannasta kommentit, jossa parent_comment_id == comment_id
	@OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Comment> childComments = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	private Account account;

	// Main-kommentin konstruktori
	public Comment(Task task, String content, Account account) {
		this.task = task;
		this.content = content;
		this.account = account;
	}

	// Replyn konstruktori
	public Comment(Comment parentComment, String content, Account account) {
		this.parentComment = parentComment;
		this.task = parentComment.getTask();
		this.content = content;
		this.account = account;
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
		// Comment is already a reply
		if (parentComment != null) {
			return;
		}

		childComments.add(reply);
		reply.setParentComment(this);
	}

	public void printChildComments() {
		for (Comment c : childComments) {
			System.out.println(c);
		}
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
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
