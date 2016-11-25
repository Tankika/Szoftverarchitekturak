package hu.bme.archi.post.bean.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR, reason="Belső hiba történt a képek mentésekor!")
public class ImageStoringException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6043819526310601891L;

}
