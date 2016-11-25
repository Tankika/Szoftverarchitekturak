package hu.bme.onlab.user.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import hu.bme.onlab.issue.domain.Comment;
import hu.bme.onlab.issue.domain.Issue;
import hu.bme.onlab.issue.domain.Project;
import hu.bme.onlab.post.domain.Post;

@Entity(name="users")
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames={"email"})
	})
@SequenceGenerator(name="user_sequence", sequenceName="user_sequence", allocationSize=1)
public class User {

	private Long id;
	private String email;
	private String password;
	private boolean enabled;
	
	private Role role;
	private Set<Project> projects;
	private Set<Issue> issues;
	private Set<Comment> comments;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_sequence")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull
	@Pattern(regexp="^[a-z0-9!#$%&'*+\\/=?^_`{|}~.-]+@[a-z0-9]([a-z0-9-]*[a-z0-9])?(\\.[a-z0-9]([a-z0-9-]*[a-z0-9])?)*$")
	@Size(max=254)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(length=254)
	@NotNull
	@Size(min=8, max=100)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@ManyToOne
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	@ManyToMany
	public Set<Project> getProjects() {
		if(projects == null) {
			projects = new HashSet<>();
		}
		return projects;
	}
	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}
	
	public void addProject(Project project) {
		this.getProjects().add(project);
		if(!project.getUsers().contains(this)) {
			project.addUser(this);
		}
	}
	
	@OneToMany(mappedBy="assignee")
	public Set<Issue> getIssues() {
		if(issues == null) {
			issues = new HashSet<>();
		}
		return issues;
	}
	public void setIssues(Set<Issue> issues) {
		this.issues = issues;
	}
	public void addIssue(Issue issue) {
		this.getIssues().add(issue);
		if(!this.equals(issue.getProject())) {
			issue.setAssignee(this);
		}
	}
	
	@OneToMany(mappedBy="author")
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
		if(!this.equals(comment.getAuthor())) {
			comment.setAuthor(this);
		}
	}
}
