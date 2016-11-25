package hu.bme.archi.post.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import hu.bme.archi.post.bean.CheckAddressRequest;
import hu.bme.archi.post.bean.CheckAddressResponse;
import hu.bme.archi.post.bean.GetCategoriesResponse;
import hu.bme.archi.post.bean.GetPostResponse;
import hu.bme.archi.post.bean.ListPostsRequest;
import hu.bme.archi.post.bean.ListPostsResponse;
import hu.bme.archi.post.bean.SendPostRequest;
import hu.bme.archi.post.bean.exception.LocationFindingException;
import hu.bme.archi.post.domain.Image;
import hu.bme.archi.post.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {

	private final static Pattern formatNameExtractorPattern = Pattern.compile("image\\/([^;]+)");
	
	private PostService postService;
	
	@Autowired
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@RequestMapping(path = "/downloadImage/{id}", method = RequestMethod.GET, produces = "image/*")
	public ResponseEntity<byte[]> downloadImage(@PathVariable long id) throws IOException {
		return downloadImageCommon(id, null, null);
	}
	
	@RequestMapping(path = "/downloadImage/{id}/{width}/{height}", method = RequestMethod.GET, produces = "image/*")
	public ResponseEntity<byte[]> downloadImageWithScaling(@PathVariable long id, @PathVariable Integer width, @PathVariable Integer height) throws IOException {
		return downloadImageCommon(id, width, height);
	}
	
	@RequestMapping(path = "/sendPost", method = RequestMethod.POST)
	public void sendPost(MultipartHttpServletRequest request) throws LocationFindingException {
		SendPostRequest sendPostRequest = new SendPostRequest();
		
		Map<String, String[]> parameterMap = request.getParameterMap();
		sendPostRequest.setTitle(parameterMap.getOrDefault("post[title]", new String[0]).length > 0 ? parameterMap.get("post[title]")[0] : "");
		sendPostRequest.setDescription(parameterMap.getOrDefault("post[description]", new String[0]).length > 0 ? parameterMap.get("post[description]")[0] : "");
		sendPostRequest.setPostalCode(parameterMap.getOrDefault("post[postalCode]", new String[0]).length > 0 ? parameterMap.get("post[postalCode]")[0] : "");
		sendPostRequest.setPriceMin(parameterMap.getOrDefault("post[priceMin]", new String[0]).length > 0 ? Integer.parseInt(parameterMap.get("post[priceMin]")[0]) : 0);
		sendPostRequest.setPriceMax(parameterMap.getOrDefault("post[priceMax]", new String[0]).length > 0 ? Integer.parseInt(parameterMap.get("post[priceMax]")[0]) : 0);
		sendPostRequest.setCategory(parameterMap.getOrDefault("post[category]", new String[0]).length > 0 ? Long.parseLong(parameterMap.get("post[category]")[0]) : 0L);
		sendPostRequest.setName(parameterMap.getOrDefault("post[name]", new String[0]).length > 0 ? parameterMap.get("post[name]")[0] : "");
		sendPostRequest.setPhone(parameterMap.getOrDefault("post[phone]", new String[0]).length > 0 ? parameterMap.get("post[phone]")[0] : "");
		sendPostRequest.setImages(request.getFileMap().values());
		
		postService.sendPost(sendPostRequest);
	}
	
	@RequestMapping(path = "/getPost/{id}", method = RequestMethod.GET)
	public GetPostResponse getPost(@PathVariable long id) {
		return postService.getPost(id);
	}
	
	@RequestMapping(path = "/listPosts", method = RequestMethod.POST)
	public ListPostsResponse listPosts(@RequestBody ListPostsRequest request) {
		return postService.listPosts(request);
	}
	
	@RequestMapping(path = "/getCategories", method = RequestMethod.GET)
	public GetCategoriesResponse getCategories() {
		return postService.getCategories();
	}
	
	@RequestMapping(path = "/checkAddress", method = RequestMethod.POST)
	public CheckAddressResponse checkAddress(@RequestBody CheckAddressRequest request) throws LocationFindingException {
		CheckAddressResponse response = new CheckAddressResponse();
		
		response.setCity(postService.checkAddress(request.getPostalCode()));
		
		return response;
	}

	private ResponseEntity<byte[]> downloadImageCommon(long id, Integer width, Integer height) throws IOException {
		Image storedImage = postService.getImage(id);
		
		if(storedImage == null) {
			// Image not found in the system
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(null);
		} else if(width != null && width > 0 && height != null && height > 0) {
			// Scaling the image...
			
			// Converting byte[] to Image object
			java.awt.Image originalImage = ImageIO.read(new ByteArrayInputStream(storedImage.getData()));

			// Resizing the image
			BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D graphics2d = resizedImage.createGraphics();
			graphics2d.drawImage(originalImage, 0, 0, width, height, null);
			graphics2d.dispose();

			// Converting to byte[]
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			Matcher matcher = formatNameExtractorPattern.matcher(storedImage.getContentType());
			if(matcher.matches()) {
				ImageIO.write(resizedImage, matcher.group(1), byteArrayOutputStream);
			} else {
				return ResponseEntity
						.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(null);
			}
			
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(byteArrayOutputStream.toByteArray());
		} else {
			// No scaling is needed
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(storedImage.getData());
		}
	}
	
}
