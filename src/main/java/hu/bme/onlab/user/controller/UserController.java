package hu.bme.onlab.user.controller;

import java.security.Principal;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@RequestMapping(path = "/user")
	public Principal user(Principal user) {
		if(user == null) {
			return new UsernamePasswordAuthenticationToken(null, null);
		}
		
		return user;
	}
}
