package hu.bme.onlab.post.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@SequenceGenerator(name="image_sequence", sequenceName="image_sequence", allocationSize=1)
public class Image {

	private Long id;
	private String name;
	private String contentType;
	private long size;
	private byte[] data;
	
	private Post post;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="image_sequence")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotNull
	@Size(max=255)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull
	@Pattern(regexp="^image\\/(jpeg|gif|png)")
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	@NotNull
	@Size(max=10_000_000)
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	
	@ManyToOne
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
		if(!post.getImages().contains(this)) {
			post.addImage(this);
		}
	}
}
