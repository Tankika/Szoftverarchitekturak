package hu.bme.onlab.post.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import hu.bme.onlab.post.bean.SendPostRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PostServiceContext.class })
public class PostServiceTest {

	@Autowired	
	private PostService postService;
	
	@Autowired	
	private DataSource dataSource;
	
	private static final String USER_NAME = "user";
	
//	@BeforeClass
//	public static void setupContext() {
//		UserDetails userDetails = new org.springframework.security.core.userdetails.User(USER_NAME, "pw", new ArrayList<GrantedAuthority>());
//		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//	}
//	
//	@Before
//	public void setupDatabase() throws Exception {
//		IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File("./src/test/resources/hu/bme/onlab/post/service/dataset.xml"));
//		IDatabaseConnection databaseConnection = new DatabaseDataSourceConnection(dataSource);
//		DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, dataSet);
//	}
	
	@Ignore	
	@Test
	public void testSendPost() throws Exception {
		String entry = "test entry";
		SendPostRequest sendPostRequest = new SendPostRequest();
//		sendPostRequest.setEntry(entry);
		
		postService.sendPost(sendPostRequest);
		
		try(Connection connection = dataSource.getConnection()) {
			try(Statement statement = connection.createStatement()) {
				String query = "select author_username, entry, creationDateTime from post p";
				try (ResultSet resultSet = statement.executeQuery(query)) {
					Assert.assertTrue("The resultSet should have at least one element", resultSet.next());
					Assert.assertEquals("Username saved for the post should match the one set in the context", USER_NAME, resultSet.getString(1));
					Assert.assertEquals("Entry text saved for the post should match the one sent in the request", entry, resultSet.getString(2));
					Assert.assertNotNull("Service call should fill the date field", resultSet.getDate(3));
				}
			}
		}
	}
	
	@Ignore
	@Test
	public void testListPosts() {		
//		postService.listPosts();
	}
	
	@Test
	public void test() throws Exception {
		String result = postService.checkAddress("2081 Hungary");
			
		Assert.assertEquals("Piliscsaba", result);
	}
	
}
