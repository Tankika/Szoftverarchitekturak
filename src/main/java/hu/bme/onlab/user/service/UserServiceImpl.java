package hu.bme.onlab.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.bme.onlab.user.bean.CheckEmailRequest;
import hu.bme.onlab.user.bean.CheckEmailResponse;
import hu.bme.onlab.user.bean.SignupRequest;
import hu.bme.onlab.user.domain.Authority;
import hu.bme.onlab.user.domain.User;
import hu.bme.onlab.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private static final String ROLE_USER = "ROLE_USER";
	
	private UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void signup(SignupRequest signupRequest) {
		User user = new User();
		user.setUsername(signupRequest.getUsername());
		user.setPassword(signupRequest.getPassword());
		user.setEnabled(true);
		Authority authority = new Authority();
		authority.setAuthority(ROLE_USER);
		authority.setUser(user);
		
		userRepository.save(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CheckEmailResponse checkEmail(CheckEmailRequest checkEmailRequest) {
		CheckEmailResponse response = new CheckEmailResponse();
		
		List<User> userList = userRepository.findByUsernameIgnoreCase(checkEmailRequest.getEmail());
		response.setEmailFree(userList.isEmpty());
		
		return response; 
	}
}
