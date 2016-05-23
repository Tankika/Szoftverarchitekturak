package hu.bme.onlab.post.bean;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND, reason="A megadott irányítószámhoz nem található magyar város!")
public class CityNotFoundException extends LocationFindingException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2912880931858066566L;

}
