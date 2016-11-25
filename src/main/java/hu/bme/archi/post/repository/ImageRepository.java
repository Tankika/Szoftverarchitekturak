package hu.bme.archi.post.repository;

import org.springframework.data.repository.CrudRepository;

import hu.bme.archi.post.domain.Image;

public interface ImageRepository extends CrudRepository<Image, Long> {

}
