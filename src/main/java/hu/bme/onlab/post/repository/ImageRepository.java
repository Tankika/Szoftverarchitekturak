package hu.bme.onlab.post.repository;

import org.springframework.data.repository.CrudRepository;

import hu.bme.onlab.post.domain.Image;

public interface ImageRepository extends CrudRepository<Image, Long> {

}
