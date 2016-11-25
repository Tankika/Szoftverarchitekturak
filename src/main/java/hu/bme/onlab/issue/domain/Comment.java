package hu.bme.onlab.issue.domain;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import hu.bme.onlab.user.domain.User;

@Entity
@SequenceGenerator(name="issue_sequence", sequenceName="issue_sequence", allocationSize=1)
public class Comment {
	private Long id;
	
	private String message;
	private Calendar timeStamp;
	
	private User author;
	private Issue issue;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="issue_sequence")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Calendar getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Calendar timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	@ManyToOne
	@NotNull
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
		if(!author.getComments().contains(this)) {
			author.addComment(this);
		}
	}

	@ManyToOne
	@NotNull
	public Issue getIssue() {
		return issue;
	}
	public void setIssue(Issue issue) {
		this.issue = issue;
		if(!issue.getComments().contains(this)) {
			issue.addComment(this);
		}
	}
}
