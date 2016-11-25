package hu.bme.archi.post.repository;

import org.springframework.data.repository.CrudRepository;

import hu.bme.archi.post.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
