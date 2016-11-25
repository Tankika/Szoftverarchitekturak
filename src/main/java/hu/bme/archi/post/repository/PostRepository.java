package hu.bme.archi.post.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import hu.bme.archi.post.domain.Post;

public interface PostRepository extends PagingAndSortingRepository<Post, Long>, QueryDslPredicateExecutor<Post> {

}
