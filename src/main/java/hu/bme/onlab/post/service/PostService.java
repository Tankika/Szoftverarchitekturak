package hu.bme.onlab.post.service;

import hu.bme.onlab.post.bean.ListPostsResponse;
import hu.bme.onlab.post.bean.SendPostRequest;

public interface PostService {

	/**
	 * 
	 * @param request
	 */
	void sendPost(SendPostRequest request);
	
	/**
	 * 	
	 * @return
	 */
	ListPostsResponse listPosts();
}
