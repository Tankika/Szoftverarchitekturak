package hu.bme.onlab.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.bme.onlab.user.bean.CheckEmailRequest;
import hu.bme.onlab.user.bean.CheckEmailResponse;
import hu.bme.onlab.user.bean.SignupRequest;
import hu.bme.onlab.user.domain.Authority;
import hu.bme.onlab.user.domain.User;
import hu.bme.onlab.user.repository.AuthorityRepository;
import hu.bme.onlab.user.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final String ROLE_USER = "ROLE_USER";
	
	private UserRepository userRepository;
	
	private AuthorityRepository authorityRepository;
	
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.authorityRepository = authorityRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void signup(SignupRequest signupRequest) {
		User user = new User();
		user.setEmail(signupRequest.getEmail());
		user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
		user.setEnabled(true);
		Authority userAuthority = authorityRepository.findByAuthorityIgnoreCase(ROLE_USER).get(0);
		user.addAuthority(userAuthority);
		
		userRepository.save(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CheckEmailResponse checkEmail(CheckEmailRequest checkEmailRequest) {
		CheckEmailResponse response = new CheckEmailResponse();
		
		List<User> userList = userRepository.findByEmailIgnoreCase(checkEmailRequest.getEmail());
		response.setEmailFree(userList.isEmpty());
		
		return response; 
	}
}
