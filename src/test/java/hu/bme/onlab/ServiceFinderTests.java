package hu.bme.onlab;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import hu.bme.onlab.ServiceFinder;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ServiceFinder.class)
@WebAppConfiguration
public class ServiceFinderTests {

	@Test
	public void contextLoads() {
	}

}
