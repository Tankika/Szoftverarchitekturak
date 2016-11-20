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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames={"authority"})
	})
@SequenceGenerator(name="authority_sequence", sequenceName="authority_sequence", allocationSize=1)
public class Authority {
	
	private Long id;
	private String authority;
	
	private Set<Role> roles;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="authority_sequence")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotNull
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	@ManyToMany
	@JoinTable(
			name="ROLE_AUTH",
			joinColumns=@JoinColumn(name="AUTH_ID", referencedColumnName="ID"),
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
		if(!role.getAuthorities().contains(this)) {
			role.addAuthority(this);
		}
	}
}
