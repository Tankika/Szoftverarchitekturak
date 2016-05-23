package hu.bme.onlab.post.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import hu.bme.onlab.post.domain.Location;

public interface LocationRepository extends CrudRepository<Location, Long> {

	List<Location> findByPostalCode(String postalcode);
}
