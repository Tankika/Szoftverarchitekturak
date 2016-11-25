package hu.bme.onlab.issue.repository;

import org.springframework.data.repository.CrudRepository;

import hu.bme.onlab.issue.domain.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
