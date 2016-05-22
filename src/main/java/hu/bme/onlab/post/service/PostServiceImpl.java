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

import hu.bme.onlab.post.bean.Post;
import hu.bme.onlab.post.bean.SendPostRequest;
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
		User user = userRepository.findByEmailIgnoreCase(principal.getUsername()).get(0);
		
		hu.bme.onlab.post.domain.Post post = new hu.bme.onlab.post.domain.Post();
		post.setTitle(request.getTitle());
		post.setDescription(request.getDescription());
		post.setZipCode(request.getZipCode());
		post.setPriceMin(request.getPriceMin());
		post.setPriceMax(request.getPriceMax());
		post.setName(request.getName());
		post.setPhone(request.getPhone());
		post.setCreationDateTime(Calendar.getInstance());
		post.setAdvertiser(user);
		
		postRepository.save(post);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Post> listPosts() {
		List<Post> response = new ArrayList<Post>();
		
		List<hu.bme.onlab.post.domain.Post> postDomains = postRepository.findAll(new PageRequest(0, 10)).getContent();
		postDomains
			.stream()
			.forEach(domainObject -> {
				Post postBean = new Post();
				postBean.setEmail(domainObject.getAdvertiser().getEmail());
//				postBean.setadvert(domainObject.getEntry());
				postBean.setCreationDateTime(domainObject.getCreationDateTime());
				response.add(postBean);	
			});
		
		return response;
	}
	
}
