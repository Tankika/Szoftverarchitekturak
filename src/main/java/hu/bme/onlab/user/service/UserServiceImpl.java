package hu.bme.onlab.user.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import hu.bme.onlab.user.bean.CheckEmailRequest;
import hu.bme.onlab.user.bean.CheckEmailResponse;
import hu.bme.onlab.user.bean.PasswordChangeRequest;
import hu.bme.onlab.user.bean.RoleDTO;
import hu.bme.onlab.user.bean.SignupRequest;
import hu.bme.onlab.user.domain.Authority;
import hu.bme.onlab.user.domain.Role;
import hu.bme.onlab.user.domain.User;
import hu.bme.onlab.user.repository.AuthorityRepository;
import hu.bme.onlab.user.repository.RoleRepository;
import hu.bme.onlab.user.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final String ROLE_USER = "ROLE_USER";
	
	private UserRepository userRepository;
	
	private AuthorityRepository authorityRepository;
	
	private RoleRepository roleRepository;
	
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
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
		
		Role role = roleRepository.findOne(signupRequest.getRoleId());
		if (role == null) {
			throw new IllegalArgumentException("Role not found by role id.");
		}
		else {
			user.addRole(role);
		}
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

	@Override
	public void changePassword(String email, PasswordChangeRequest passwordChangeRequest) {
		User user = userRepository.findByEmail(email);
		user.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
		userRepository.save(user);
	}

	@Override
	public List<RoleDTO> getRoles() {
		List<RoleDTO> response = new ArrayList<RoleDTO>();
		Iterable<Role> queryResult = roleRepository.findAll();
		for(Role role : queryResult) {
			RoleDTO roleDTO = new RoleDTO();
			roleDTO.setId(role.getId());
			roleDTO.setRoleName(role.getRoleName());
			response.add(roleDTO);
		}
		
		return response;
	}
	
}
