package hu.bme.onlab.user.service;

import java.util.List;

import hu.bme.onlab.user.bean.CheckEmailRequest;
import hu.bme.onlab.user.bean.CheckEmailResponse;
import hu.bme.onlab.user.bean.CreateUserRequest;
import hu.bme.onlab.user.bean.ModifyUserRequest;
import hu.bme.onlab.user.bean.PasswordChangeRequest;
import hu.bme.onlab.user.bean.RoleDTO;
import hu.bme.onlab.user.bean.UserDataRequest;
import hu.bme.onlab.user.bean.UserDataResponse;

public interface UserService {

	void createUser(CreateUserRequest createUserRequest);
	
	CheckEmailResponse checkEmail(CheckEmailRequest checkEmailRequest);
	
	void changePassword(String email, PasswordChangeRequest passwordChangeRequest);
	
	List<RoleDTO> getRoles();
	
	UserDataResponse getUserData(UserDataRequest userDataRequest);
	
	void modifyUser(ModifyUserRequest modifyUserRequest);
}
