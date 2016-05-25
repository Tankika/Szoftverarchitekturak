package hu.bme.onlab.post.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames={"name"})
	})
@SequenceGenerator(name="category_sequence", sequenceName="category_sequence", allocationSize=1)
public class Category {
	
	private Long id;
	private String name;
	
	private Set<Post> posts;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="category_sequence")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotNull
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy="category", cascade=CascadeType.REMOVE)
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
		if(post.getCategory() != this) {
			post.setCategory(this);
		}
	}
}
