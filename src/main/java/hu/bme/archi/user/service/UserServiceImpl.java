package hu.bme.archi.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

import hu.bme.archi.issue.repository.ProjectRepository;
import hu.bme.archi.user.bean.CheckEmailRequest;
import hu.bme.archi.user.bean.CheckEmailResponse;
import hu.bme.archi.user.bean.CreateUserRequest;
import hu.bme.archi.user.bean.ModifyUserRequest;
import hu.bme.archi.user.bean.PasswordChangeRequest;
import hu.bme.archi.user.bean.RoleDTO;
import hu.bme.archi.user.bean.UserDataRequest;
import hu.bme.archi.user.bean.UserDataResponse;
import hu.bme.archi.user.domain.User;
import hu.bme.archi.user.repository.RoleRepository;
import hu.bme.archi.user.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	
	
	private RoleRepository roleRepository;
	
	private PasswordEncoder passwordEncoder;
	
	private ProjectRepository projectRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ProjectRepository projectRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.projectRepository = projectRepository;
	}
	
	@Override
	public void createUser(CreateUserRequest createUserRequest) {
		User user = new User();
		
		user.setEmail(createUserRequest.getEmail());
		user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
		user.setEnabled(true);
		user.setRole(roleRepository.findOne(createUserRequest.getRoleId()));
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
		User user = userRepository.findByEmail(userDataRequest.getEmail());
		user.getProjects().forEach(p -> userDataResponse.getProjectIds().add(p.getId()));
		userDataResponse.setRoleId(user.getRole().getId());	
		
		return userDataResponse;
	}

	@Override
	public void modifyUser(ModifyUserRequest modifyUserRequest) {
		User userToModify = userRepository.findByEmail(modifyUserRequest.getEmail());
		
		userToModify.setProjects(Sets.newHashSet(projectRepository.findAll(modifyUserRequest.getProjectIds())));
		userToModify.setRole(roleRepository.findOne(modifyUserRequest.getRoleId()));
		
		userRepository.save(userToModify);
	}
	
}
