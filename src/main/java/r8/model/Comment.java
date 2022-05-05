package r8.model;

import org.hibernate.annotations.CreationTimestamp;
import r8.model.task.Task;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @see Task
 * @author sanku, Joona Nylander
 *
 */

@Entity
@Table(name = "comment")
public class Comment {

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

	@OneToMany(mappedBy = "parentComment", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Set<Comment> childComments = new HashSet<>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id")
	private Account account;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "comment_date")
	private Date timeStamp;

	// Main-kommentin konstruktori
	public Comment(Task task, Account account, String content) {
		this.task = task;
		this.content = content;
		this.account = account;
	}

	// Replyn konstruktori
	public Comment(Comment parentComment, Account account, String content) {
		this.parentComment = parentComment;
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

	/**
	 * Adds a reply comment to another comment.
	 * @param reply Parent comment to be replied to
	 */
	public void addReply(Comment reply) {
		// Comment is already a reply
		if (parentComment != null) {
			return;
		}

		childComments.add(reply);
		reply.setParentComment(this);
	}

	public Date getTimeStamp() {
		return this.timeStamp;
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
		return content + " // author: " + account + " Date: " + timeStamp;
	}
}
