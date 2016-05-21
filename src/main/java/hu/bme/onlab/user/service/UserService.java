package hu.bme.onlab.user.service;

import hu.bme.onlab.user.bean.CheckEmailRequest;
import hu.bme.onlab.user.bean.CheckEmailResponse;
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
}
