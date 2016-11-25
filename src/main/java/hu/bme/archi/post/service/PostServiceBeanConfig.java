package hu.bme.archi.post.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.maps.GeoApiContext;

import hu.bme.archi.IssueTracker;

@Configuration
public class PostServiceBeanConfig {

	@Bean
	public GeoApiContext getGeoApiContext() {
		return new GeoApiContext().setApiKey(IssueTracker.API_KEY);
	}
}
