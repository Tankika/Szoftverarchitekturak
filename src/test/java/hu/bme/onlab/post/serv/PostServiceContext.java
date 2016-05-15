package hu.bme.onlab.post.serv;

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

import hu.bme.onlab.post.repository.PostRepository;
import hu.bme.onlab.post.service.PostService;
import hu.bme.onlab.post.service.PostServiceImpl;
import hu.bme.onlab.user.repository.UserRepository;

@Configuration
@EnableJpaRepositories(basePackages = {"hu.bme.onlab.post.repository", "hu.bme.onlab.user.repository"})
public class PostServiceContext {

	@Autowired
	PostRepository postRepository;

	@Autowired
	UserRepository userRepository;
	
	@Bean
	public PostService getPostService(){
		return new PostServiceImpl(postRepository, userRepository);
	}

	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		return builder.setType(EmbeddedDatabaseType.H2).build();
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
