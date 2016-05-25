package hu.bme.onlab.post.repository;

import org.springframework.data.repository.CrudRepository;

import hu.bme.onlab.post.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
