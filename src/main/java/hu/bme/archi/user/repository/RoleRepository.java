package hu.bme.archi.user.repository;

import org.springframework.data.repository.CrudRepository;

import hu.bme.archi.user.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByRoleNameIgnoreCase(String roleName);
}
