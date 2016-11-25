package hu.bme.archi.user.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import hu.bme.archi.user.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findByEmailIgnoreCase(String email);
	
	User findByEmail(String email);
}
