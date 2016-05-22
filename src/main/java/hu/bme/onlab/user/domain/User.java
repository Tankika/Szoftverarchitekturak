package hu.bme.onlab.user.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import hu.bme.onlab.post.domain.Post;

@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames={"email"})
	})
@Entity(name = "users")
@SequenceGenerator(name="user_sequence", sequenceName="user_sequence")
public class User {

	private Long id;
	private String email;
	private String password;
	private boolean enabled;
	
	private Set<Authority> authorities;
	private Set<Post> posts;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_sequence")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(length=254)
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

	@OneToMany(mappedBy = "users")
	public Set<Authority> getAuthorities() {
		if(authorities == null) {
			authorities = new HashSet<Authority>();
		}
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}
	
	public void addAuthority(Authority authority) {
		this.getAuthorities().add(authority);
		if(!authority.getUsers().contains(this)) {
			authority.addUser(this);
		}
	}
	
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "author")
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
		if(post.getAuthor() != this) {
			post.setAuthor(this);
		}
	}
}
