package hu.bme.onlab.user.service;

import java.util.List;

import hu.bme.onlab.user.bean.CheckEmailRequest;
import hu.bme.onlab.user.bean.CheckEmailResponse;
import hu.bme.onlab.user.bean.PasswordChangeRequest;
import hu.bme.onlab.user.bean.RoleDTO;
import hu.bme.onlab.user.bean.SignupRequest;

public interface UserService {

	/**
	 * 
	 * @param signupRequest
	 */
	void signup(SignupRequest signupRequest);
	
	/**
	 * 
	 * @param checkEmailRequest
	 * @return
	 */
	CheckEmailResponse checkEmail(CheckEmailRequest checkEmailRequest);
	
	void changePassword(String email, PasswordChangeRequest passwordChangeRequest);
	
	List<RoleDTO> getRoles();
}
