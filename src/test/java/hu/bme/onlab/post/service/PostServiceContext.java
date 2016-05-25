package hu.bme.onlab.post.service;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.google.maps.GeoApiContext;

import hu.bme.onlab.post.repository.PostRepository;
import hu.bme.onlab.user.repository.UserRepository;

@Configuration
@EnableJpaRepositories(basePackages = {"hu.bme.onlab.post.repository", "hu.bme.onlab.user.repository"})
public class PostServiceContext {

	private static final String GOOGLE_KEY_PROPERTY = "google.key";
	
	@Autowired
	PostRepository postRepository;

	@Autowired
	UserRepository userRepository;
	
	@Bean
	public PostService getPostService(){
		return new PostServiceImpl(postRepository, userRepository, null, null, getGeoApiContext());
	}
	
	@Bean
	public GeoApiContext getGeoApiContext() {
		String apiKey = System.getProperty(GOOGLE_KEY_PROPERTY);
		
		if(apiKey == null) {
			throw new IllegalStateException("Google api key is not set in system properties");
		} else {
			return new GeoApiContext().setApiKey(apiKey);	
		}
	}

	@Bean
	public DataSource dataSource() {
		DataSource dataSource = new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.build();
		
		return dataSource;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);
	
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("hu.bme.onlab");
		factory.setDataSource(dataSource());
		factory.afterPropertiesSet();
	
		return factory.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {

		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory());
		return txManager;
	}
}
