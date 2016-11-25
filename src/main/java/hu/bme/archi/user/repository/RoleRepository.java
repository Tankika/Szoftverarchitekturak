package hu.bme.archi.user.repository;

import org.springframework.data.repository.CrudRepository;

import hu.bme.archi.user.domain.Role;
import hu.bme.archi.user.domain.User;

public interface RoleRepository extends CrudRepository<Role, Long> {

}
