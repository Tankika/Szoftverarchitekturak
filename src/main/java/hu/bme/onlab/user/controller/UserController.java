package hu.bme.onlab.user.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.bme.onlab.user.bean.CheckEmailRequest;
import hu.bme.onlab.user.bean.CheckEmailResponse;
import hu.bme.onlab.user.bean.SignupRequest;
import hu.bme.onlab.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(path = "/user")
	public Principal user(Principal user) {
		if(user == null) {
			return new UsernamePasswordAuthenticationToken(null, null);
		}
		
		return user;
	}
	
	@RequestMapping(path = "/signup", method = RequestMethod.POST)
	public void signup(@RequestBody SignupRequest signupRequest) {
		userService.signup(signupRequest);
	}
	
	@RequestMapping(path = "/checkEmail", method = RequestMethod.POST)
	public CheckEmailResponse checkEmail(@RequestBody CheckEmailRequest checkEmailRequest) {
		return userService.checkEmail(checkEmailRequest);
	}
}
