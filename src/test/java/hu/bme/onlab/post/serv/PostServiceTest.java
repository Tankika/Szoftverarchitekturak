package hu.bme.onlab.post.serv;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import hu.bme.onlab.post.bean.SendPostRequest;
import hu.bme.onlab.post.service.PostService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PostServiceContext.class })
public class PostServiceTest {

	@Autowired	
	private PostService postService;
	
	@Autowired	
	private DataSource dataSource;
	
	private static final String USER_NAME = "Username";
	
	@BeforeClass
	public static void setupContext() {
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(USER_NAME, "pw", new ArrayList<GrantedAuthority>());
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	@Test
	public void testSendPost() throws Exception {
		String entry = "test entry";
		SendPostRequest sendPostRequest = new SendPostRequest();
		sendPostRequest.setEntry(entry);
		
		postService.sendPost(sendPostRequest);
		
		try(Connection connection = dataSource.getConnection()) {
			try(Statement statement = connection.createStatement()) {
				String query = "select author_username, entry, creationDateTime from post p";
				try (ResultSet resultSet = statement.executeQuery(query)) {
					Assert.assertEquals("Username saved for the post should match the one set in the context", USER_NAME, resultSet.getString(1));
					Assert.assertEquals("Entry text saved for the post should match the one sent in the request", entry, resultSet.getString(2));
					Assert.assertNotNull("Service call should fill the date field", resultSet.getDate(3));
				}
			}
		}
	}
	
	@Test
	public void testListPosts() {		
		postService.listPosts();
	}
	
}
