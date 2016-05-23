package hu.bme.onlab.post.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.bme.onlab.post.bean.CheckAddressRequest;
import hu.bme.onlab.post.bean.CheckAddressResponse;
import hu.bme.onlab.post.bean.ListPostsRequest;
import hu.bme.onlab.post.bean.ListPostsResponse;
import hu.bme.onlab.post.bean.LocationFindingException;
import hu.bme.onlab.post.bean.SendPostRequest;
import hu.bme.onlab.post.service.PostService;

@RestController
public class PostController {

	private PostService postService;
	
	@Autowired
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@RequestMapping(path = "/sendPost", method = RequestMethod.POST)
	public void sendPost(@RequestBody SendPostRequest request) throws LocationFindingException {
		postService.sendPost(request);
	}
	
	@RequestMapping(path = "/listPosts", method = RequestMethod.POST)
	public ListPostsResponse listPosts(@RequestBody ListPostsRequest request) {
		return postService.listPosts(request);
	}
	
	@RequestMapping(path = "/checkAddress", method = RequestMethod.POST)
	public CheckAddressResponse checkAddress(@RequestBody CheckAddressRequest request) throws LocationFindingException {
		CheckAddressResponse response = new CheckAddressResponse();
		
		response.setCity(postService.checkAddress(request.getPostalCode()));
		
		return response;
	}
	
}
