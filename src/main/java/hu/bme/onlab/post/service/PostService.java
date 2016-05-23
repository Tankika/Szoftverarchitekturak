package hu.bme.onlab.post.service;

import hu.bme.onlab.post.bean.ListPostsRequest;
import hu.bme.onlab.post.bean.ListPostsResponse;
import hu.bme.onlab.post.bean.LocationFindingException;
import hu.bme.onlab.post.bean.SendPostRequest;

public interface PostService {

	/**
	 * 
	 * @param request
	 */
	void sendPost(SendPostRequest request) throws LocationFindingException;
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	ListPostsResponse listPosts(ListPostsRequest request);
	
	/**
	 * 
	 * @param postalCode
	 * @return
	 * @throws LocationFindingException
	 */
	public String checkAddress(String postalCode) throws LocationFindingException;
}
