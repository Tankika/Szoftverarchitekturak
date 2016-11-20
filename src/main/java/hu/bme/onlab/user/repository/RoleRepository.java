package hu.bme.onlab.user.repository;

import org.springframework.data.repository.CrudRepository;

import hu.bme.onlab.user.domain.Role;
import hu.bme.onlab.user.domain.User;

public interface RoleRepository extends CrudRepository<Role, Long> {

}
