package hu.bme.onlab.post.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.bme.onlab.post.bean.ListPostsResponse;
import hu.bme.onlab.post.bean.SendPostRequest;
import hu.bme.onlab.post.domain.Post;
import hu.bme.onlab.post.repository.PostRepository;
import hu.bme.onlab.user.domain.User;
import hu.bme.onlab.user.repository.UserRepository;

@Service
@Transactional
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;
	
	private UserRepository userRepository;
	
	@Autowired
	public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendPost(SendPostRequest request) {

		UserDetails principal = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User author = userRepository.findOne(principal.getUsername());
		
		Post post = new Post();
		post.setEntry(request.getEntry());
		post.setAuthor(author);
		post.setCreationDateTime(Calendar.getInstance());
		
		postRepository.save(post);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListPostsResponse listPosts() {
		ListPostsResponse response = new ListPostsResponse();
		response.setPosts(new ArrayList<>());
		
		List<Post> postDomains = postRepository.findAll(new PageRequest(0, 10)).getContent();
		postDomains
			.stream()
			.forEach(domainObject -> {
				hu.bme.onlab.post.bean.Post postBean = new hu.bme.onlab.post.bean.Post();
				postBean.setUsername(domainObject.getAuthor().getUsername());
				postBean.setEntry(domainObject.getEntry());
				postBean.setCreationDateTime(domainObject.getCreationDateTime());
				response.getPosts().add(postBean);	
			});
		
		return response;
	}
	
}
