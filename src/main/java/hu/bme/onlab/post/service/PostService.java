package hu.bme.onlab.post.service;

import hu.bme.onlab.post.bean.GetCategoriesResponse;
import hu.bme.onlab.post.bean.GetPostResponse;
import hu.bme.onlab.post.bean.ListPostsRequest;
import hu.bme.onlab.post.bean.ListPostsResponse;
import hu.bme.onlab.post.bean.SendPostRequest;
import hu.bme.onlab.post.bean.exception.LocationFindingException;
import hu.bme.onlab.post.domain.Image;

public interface PostService {

	Image getImage(long id);
	
	/**
	 * 
	 * @param request
	 */
	void sendPost(SendPostRequest request) throws LocationFindingException;
	

	public GetPostResponse getPost(long id);
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	ListPostsResponse listPosts(ListPostsRequest request);
	
	public GetCategoriesResponse getCategories();
	
	/**
	 * 
	 * @param postalCode
	 * @return
	 * @throws LocationFindingException
	 */
	public String checkAddress(String postalCode) throws LocationFindingException;
}
