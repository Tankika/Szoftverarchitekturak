package hu.bme.onlab.post.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames={"postalCode"})
	})
@Entity
@SequenceGenerator(name="location_sequence", sequenceName="location_sequence")
public class Location {

	private Long id;
	private String postalCode;
	private String city;
	
	private Set<Post> posts;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="location_sequence")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull
	@Pattern(regexp="^\\d{4}$")
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@NotNull
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@OneToMany(mappedBy="location")
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
		if(post.getLocation() != this) {
			post.setLocation(this);
		}
	}
}
