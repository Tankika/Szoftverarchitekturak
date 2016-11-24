package hu.bme.onlab.issue.domain;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import hu.bme.onlab.user.domain.User;

@Entity
@SequenceGenerator(name="issue_sequence", sequenceName="issue_sequence", allocationSize=1)
public class Issue {
	private Long id;

	private String name;
	private String description;
	private String reproductionSteps;
	private String version;
	private Calendar creationTimeStamp;
	
	@Enumerated(EnumType.STRING)
	private Type type;
	@Enumerated(EnumType.STRING)
	private Status status;
	@Enumerated(EnumType.STRING)
	private Priority priority;
	@Enumerated(EnumType.STRING)
	private Severity severity;
	
	private Project project;
	private User assignee;
	private Set<Comment> comments;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="issue_sequence")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getReproductionSteps() {
		return reproductionSteps;
	}
	public void setReproductionSteps(String reproductionSteps) {
		this.reproductionSteps = reproductionSteps;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	public Severity getSeverity() {
		return severity;
	}
	public void setSeverity(Severity severity) {
		this.severity = severity;
	}
	public Calendar getCreationTimeStamp() {
		return creationTimeStamp;
	}
	public void setCreationTimeStamp(Calendar creationTimeStamp) {
		this.creationTimeStamp = creationTimeStamp;
	}
	
	@ManyToOne
	@NotNull
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
	@ManyToOne
	@NotNull
	public User getAssignee() {
		return assignee;
	}
	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}
	
	@OneToMany(mappedBy="issue")
	public Set<Comment> getComments() {
		if(comments == null) {
			comments = new HashSet<>();
		}
		return comments;
	}
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	public void addComment(Comment comment) {
		this.getComments().add(comment);
		if(!this.equals(comment.getIssue())) {
			comment.setIssue(this);
		}
	}
}
