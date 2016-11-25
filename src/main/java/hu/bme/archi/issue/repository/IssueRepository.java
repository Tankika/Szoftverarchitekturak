package hu.bme.archi.issue.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import hu.bme.archi.issue.domain.Issue;

public interface IssueRepository extends CrudRepository<Issue, Long> {
	
	List<Issue> findByProjectId(long projectId);
}
