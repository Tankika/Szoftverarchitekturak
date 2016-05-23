package hu.bme.onlab.post.bean;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST, reason="A megadott irányítószám formátuma nem megfelelő!")
public class PostalCodeFormatException extends LocationFindingException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8996211265876806955L;

	
}
