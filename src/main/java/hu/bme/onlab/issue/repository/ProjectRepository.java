package hu.bme.onlab.issue.repository;

import org.springframework.data.repository.CrudRepository;

import hu.bme.onlab.issue.domain.Project;
import hu.bme.onlab.post.domain.Category;

public interface ProjectRepository extends CrudRepository<Project, Long> {

}
