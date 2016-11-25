package hu.bme.archi.user.service;

import java.util.List;

import hu.bme.archi.user.bean.CheckEmailRequest;
import hu.bme.archi.user.bean.CheckEmailResponse;
import hu.bme.archi.user.bean.CreateUserRequest;
import hu.bme.archi.user.bean.ModifyUserRequest;
import hu.bme.archi.user.bean.PasswordChangeRequest;
import hu.bme.archi.user.bean.RoleDTO;
import hu.bme.archi.user.bean.UserDataRequest;
import hu.bme.archi.user.bean.UserDataResponse;

public interface UserService {

	void createUser(CreateUserRequest createUserRequest);
	
	CheckEmailResponse checkEmail(CheckEmailRequest checkEmailRequest);
	
	void changePassword(String email, PasswordChangeRequest passwordChangeRequest);
	
	List<RoleDTO> getRoles();
	
	UserDataResponse getUserData(UserDataRequest userDataRequest);
	
	void modifyUser(ModifyUserRequest modifyUserRequest);
}
