package hu.bme.onlab.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

import hu.bme.onlab.issue.repository.ProjectRepository;
import hu.bme.onlab.user.bean.CheckEmailRequest;
import hu.bme.onlab.user.bean.CheckEmailResponse;
import hu.bme.onlab.user.bean.CreateUserRequest;
import hu.bme.onlab.user.bean.ModifyUserRequest;
import hu.bme.onlab.user.bean.PasswordChangeRequest;
import hu.bme.onlab.user.bean.RoleDTO;
import hu.bme.onlab.user.bean.UserDataRequest;
import hu.bme.onlab.user.bean.UserDataResponse;
import hu.bme.onlab.user.domain.User;
import hu.bme.onlab.user.repository.AuthorityRepository;
import hu.bme.onlab.user.repository.RoleRepository;
import hu.bme.onlab.user.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	
	private AuthorityRepository authorityRepository;
	
	private RoleRepository roleRepository;
	
	private PasswordEncoder passwordEncoder;
	
	private ProjectRepository projectRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder, ProjectRepository projectRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.authorityRepository = authorityRepository;
		this.passwordEncoder = passwordEncoder;
		this.projectRepository = projectRepository;
	}
	
	@Override
	public void createUser(CreateUserRequest createUserRequest) {
		User user = new User();
		
		user.setEmail(createUserRequest.getEmail());
		user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
		user.setEnabled(true);
		user.setRoles(Sets.newHashSet(roleRepository.findAll(createUserRequest.getRoleIds())));
		user.setProjects(Sets.newHashSet(projectRepository.findAll(createUserRequest.getProjectIds())));
		
		userRepository.save(user);
	}

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
		
		roleRepository.findAll().forEach(role -> {
			RoleDTO roleDTO = new RoleDTO();
			roleDTO.setId(role.getId());
			roleDTO.setRoleName(role.getRoleName());
			response.add(roleDTO);
		});
		
		return response;
	}

	@Override
	public UserDataResponse getUserData(UserDataRequest userDataRequest) {
		UserDataResponse userDataResponse = new UserDataResponse();
		userDataResponse.setProjectIds(new ArrayList<Long>());
		userDataResponse.setRoleIds(new ArrayList<Long>());
		User user = userRepository.findByEmail(userDataRequest.getEmail());
		user.getProjects().forEach(p -> userDataResponse.getProjectIds().add(p.getId()));
		user.getRoles().forEach(r -> userDataResponse.getRoleIds().add(r.getId()));
		
		return userDataResponse;
	}

	@Override
	public void modifyUser(ModifyUserRequest modifyUserRequest) {
		User userToModify = userRepository.findByEmail(modifyUserRequest.getEmail());
		
		userToModify.setProjects(Sets.newHashSet(projectRepository.findAll(modifyUserRequest.getProjectIds())));
		userToModify.setRoles(Sets.newHashSet(roleRepository.findAll(modifyUserRequest.getRoleIds())));
		
		userRepository.save(userToModify);
	}
	
}
