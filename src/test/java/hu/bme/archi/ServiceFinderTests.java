package hu.bme.archi;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import hu.bme.archi.IssueTracker;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IssueTracker.class)
@WebAppConfiguration
public class ServiceFinderTests {

	@Ignore
	@Test
	public void contextLoads() {
	}

}
