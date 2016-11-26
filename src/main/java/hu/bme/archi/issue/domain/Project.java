package hu.bme.archi.issue.domain;

import java.util.HashSet;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import hu.bme.archi.user.domain.User;

@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames={"name"})
})
@Entity
@SequenceGenerator(name="project_sequence", sequenceName="project_sequence", allocationSize=1)
public class Project {
	private Long id;
	
	private String name;
	private String description;
	
	private Set<User> users;
	private Set<Issue> issues;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="project_sequence")
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
	@ManyToMany(mappedBy="projects")
	public Set<User> getUsers() {
		if(users == null) {
			users = new HashSet<>();
		}
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public void addUser(User user) {
		this.getUsers().add(user);
		if(!user.getProjects().contains(this)) {
			user.addProject(this);
		}
	}
	
	@OneToMany(mappedBy="project", cascade=CascadeType.REMOVE)
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
			issue.setProject(this);
		}
	}
	
}
