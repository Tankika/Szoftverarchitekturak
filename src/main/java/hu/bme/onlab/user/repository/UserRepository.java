package hu.bme.onlab.user.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import hu.bme.onlab.user.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findByEmailIgnoreCase(String email);
}
