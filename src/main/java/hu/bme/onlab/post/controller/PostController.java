package hu.bme.onlab.post.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.bme.onlab.post.bean.Post;
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
	public void sendPost(@RequestBody SendPostRequest request) {
		postService.sendPost(request);
	}
	
	@RequestMapping(path = "/listPosts", method = RequestMethod.GET)
	public List<Post> listPosts() {
		return postService.listPosts();
	}
	
}
