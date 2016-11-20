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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
	
	private Set<Role> roles;
	private Set<Post> posts;

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

	@ManyToMany
	@JoinTable(
			name="USER_ROLE",
			joinColumns=@JoinColumn(name="USER_ID", referencedColumnName="ID"),
			inverseJoinColumns=@JoinColumn(name="ROLE_ID", referencedColumnName="ID"))
	public Set<Role> getRoles() {
		if(roles == null) {
			roles = new HashSet<Role>();
		}
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole(Role role) {
		this.getRoles().add(role);
		if(!role.getUsers().contains(this)) {
			role.addUser(this);
		}
	}
	
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "advertiser")
	public Set<Post> getPosts() {
		if(posts == null) {
			posts = new HashSet<Post>();
		}
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}
	
	public void addPost(Post post) {
		this.getPosts().add(post);
		if(post.getAdvertiser() != this) {
			post.setAdvertiser(this);
		}
	}
}
