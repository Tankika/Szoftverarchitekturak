package hu.bme.onlab.user.domain;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import hu.bme.onlab.post.domain.Post;

@Entity(name = "users")
public class User {
	
	private String username;
	private String password;
	private boolean enabled;
	
	private Collection<Authority> authorities;
	private Collection<Post> posts;

	@Id
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

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
	public Collection<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<Authority> authorities) {
		this.authorities = authorities;
	}
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "author")
	public Collection<Post> getPosts() {
		return posts;
	}

	public void setPosts(Collection<Post> posts) {
		this.posts = posts;
	}
}
