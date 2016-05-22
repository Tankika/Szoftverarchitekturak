package hu.bme.onlab.user.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import hu.bme.onlab.user.domain.Authority;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {
	
	List<Authority> findByAuthorityIgnoreCase(String authory);
	
}
