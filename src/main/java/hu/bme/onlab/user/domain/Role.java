package hu.bme.onlab.user.domain;

import java.util.HashSet;
import java.util.Set;

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

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames={"roleName"})
	})
@SequenceGenerator(name="role_sequence", sequenceName="role_sequence", allocationSize=1)
public class Role {
	
	private Long id;
	private String roleName;
	private Set<Authority> authorities;
	private Set<User> users;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="role_sequence")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@ManyToMany
	@JoinTable(
			name="ROLE_AUTH",
			joinColumns=@JoinColumn(name="ROLE_ID", referencedColumnName="ID"),
			inverseJoinColumns=@JoinColumn(name="AUTH_ID", referencedColumnName="ID"))
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
		this.authorities.add(authority);
	}
	
	@ManyToMany
	@JoinTable(
			name="USER_ROLE",
			joinColumns=@JoinColumn(name="ROLE_ID", referencedColumnName="ID"),
			inverseJoinColumns=@JoinColumn(name="USER_ID", referencedColumnName="ID"))
	public Set<User> getUsers() {
		if(users == null) {
			users = new HashSet<User>();
		}
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public void addUser(User user) {
		this.users.add(user);
	}
}
