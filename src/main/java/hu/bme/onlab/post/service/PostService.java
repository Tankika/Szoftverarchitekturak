package hu.bme.onlab.post.service;

import hu.bme.onlab.post.bean.ListPostsResponse;
import hu.bme.onlab.post.bean.SendPostRequest;

public interface PostService {

	void sendPost(SendPostRequest request);
	
	ListPostsResponse listPosts();
}
