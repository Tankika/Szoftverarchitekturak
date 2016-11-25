package hu.bme.archi.issue.repository;

import org.springframework.data.repository.CrudRepository;

import hu.bme.archi.issue.domain.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
