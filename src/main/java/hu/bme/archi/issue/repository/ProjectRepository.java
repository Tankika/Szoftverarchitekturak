package hu.bme.archi.issue.repository;

import org.springframework.data.repository.CrudRepository;

import hu.bme.archi.issue.domain.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {

}
