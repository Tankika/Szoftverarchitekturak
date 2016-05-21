package hu.bme.onlab.user.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import hu.bme.onlab.post.domain.Post;

@Entity(name = "users")
public class User {

	private Long id;
	private String username;
	private String password;
	private boolean enabled;
	
	private Set<Authority> authorities;
	private Set<Post> posts;

	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
	public Set<Authority> getAuthorities() {
		if(authorities == null) {
			authorities = new HashSet<Authority>();
		}
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "author")
	public Set<Post> getPosts() {
		if(posts == null) {
			posts = new HashSet<Post>();
		}
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}
}
