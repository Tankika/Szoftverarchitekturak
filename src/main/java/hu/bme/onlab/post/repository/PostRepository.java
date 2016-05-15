package hu.bme.onlab.post.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import hu.bme.onlab.post.domain.Post;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

}
