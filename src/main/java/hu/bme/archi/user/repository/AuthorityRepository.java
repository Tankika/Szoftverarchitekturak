package hu.bme.archi.user.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import hu.bme.archi.user.domain.Authority;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {
	
	List<Authority> findByAuthorityIgnoreCase(String authory);
	
}
