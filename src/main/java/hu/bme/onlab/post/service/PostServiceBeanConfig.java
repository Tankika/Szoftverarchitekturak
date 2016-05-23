package hu.bme.onlab.post.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.maps.GeoApiContext;

import hu.bme.onlab.ServiceFinder;

@Configuration
public class PostServiceBeanConfig {

	@Bean
	public GeoApiContext getGeoApiContext() {
		return new GeoApiContext().setApiKey(ServiceFinder.API_KEY);
	}
}
