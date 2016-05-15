package hu.bme.onlab.user.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import hu.bme.onlab.user.domain.Authority.AuthorityId;


@IdClass(AuthorityId.class)
@Entity(name = "authorities")
public class Authority {
	
	private User user;
	private String authority;
	
	@Id
	@ManyToOne
	@JoinColumn(name="username")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Id
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public class AuthorityId implements Serializable {
		private transient static final long serialVersionUID = -8043425316738528456L;
		
		private String user;
		private String authority;
		
		public String getUser() {
			return user;
		}
		public void setUser(String user) {
			this.user = user;
		}
		public String getAuthority() {
			return authority;
		}
		public void setAuthority(String authority) {
			this.authority = authority;
		}
	}
}
