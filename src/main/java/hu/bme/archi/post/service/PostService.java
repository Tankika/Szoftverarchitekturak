package hu.bme.archi.post.service;

import hu.bme.archi.post.bean.GetCategoriesResponse;
import hu.bme.archi.post.bean.GetPostResponse;
import hu.bme.archi.post.bean.ListPostsRequest;
import hu.bme.archi.post.bean.ListPostsResponse;
import hu.bme.archi.post.bean.SendPostRequest;
import hu.bme.archi.post.bean.exception.LocationFindingException;
import hu.bme.archi.post.domain.Image;

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
