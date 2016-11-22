package hu.bme.onlab.post.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import com.mysema.query.types.ExpressionUtils;
import com.mysema.query.types.Predicate;

import hu.bme.onlab.ServiceFinder;
import hu.bme.onlab.post.bean.GetCategoriesData;
import hu.bme.onlab.post.bean.GetCategoriesResponse;
import hu.bme.onlab.post.bean.GetPostResponse;
import hu.bme.onlab.post.bean.ListPostData;
import hu.bme.onlab.post.bean.ListPostsRequest;
import hu.bme.onlab.post.bean.ListPostsResponse;
import hu.bme.onlab.post.bean.SendPostRequest;
import hu.bme.onlab.post.bean.exception.CityNotFoundException;
import hu.bme.onlab.post.bean.exception.ImageStoringException;
import hu.bme.onlab.post.bean.exception.LocationFindingException;
import hu.bme.onlab.post.bean.exception.PostalCodeFormatException;
import hu.bme.onlab.post.domain.Category;
import hu.bme.onlab.post.domain.Image;
import hu.bme.onlab.post.domain.Location;
import hu.bme.onlab.post.domain.Post;
import hu.bme.onlab.post.domain.PostPredicates;
import hu.bme.onlab.post.repository.CategoryRepository;
import hu.bme.onlab.post.repository.ImageRepository;
import hu.bme.onlab.post.repository.LocationRepository;
import hu.bme.onlab.post.repository.PostRepository;
import hu.bme.onlab.user.domain.User;
import hu.bme.onlab.user.repository.UserRepository;

@Service
@Transactional
public class PostServiceImpl implements PostService {
	
	private final static Pattern postalCodePattern = Pattern.compile("^\\d{4}$");
	
	private PostRepository postRepository;
	
	private UserRepository userRepository;
	
	private LocationRepository locationRepository;
	
	private ImageRepository imageRepository;
	
	private CategoryRepository categoryRepository;
	
	private GeoApiContext geoApiContext;
	
	@Autowired
	public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, LocationRepository locationRepository, ImageRepository imageRepository, CategoryRepository categoryRepository, GeoApiContext geoApiContext) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.locationRepository = locationRepository;
		this.imageRepository = imageRepository;
		this.geoApiContext = geoApiContext;
		this.categoryRepository = categoryRepository;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image getImage(long id) {
		return imageRepository.findOne(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendPost(SendPostRequest request) throws LocationFindingException {

		UserDetails loggedInPrincipal = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findByEmailIgnoreCase(loggedInPrincipal.getUsername()).get(0);
		
		Location location = findOrCreateLocation(request.getPostalCode());
		
		Category category = categoryRepository.findOne(request.getCategory());
		
		Post post = new Post();
		post.setTitle(request.getTitle());
		post.setDescription(request.getDescription());
		post.setPriceMin(request.getPriceMin());
		post.setPriceMax(request.getPriceMax());
		post.setName(request.getName());
		post.setPhone(request.getPhone());
		post.setCreationDateTime(Calendar.getInstance());
//		post.setAdvertiser(user);
		post.setLocation(location);
		post.setCategory(category);
		
		request.getImages().stream().forEach(rawImage -> {
			Image image = new Image();
			image.setName(rawImage.getOriginalFilename());
			image.setContentType(rawImage.getContentType());
			image.setSize(rawImage.getSize());
			image.setPost(post);
			
			try {
				image.setData(rawImage.getBytes());
			} catch (IOException e) {
				throw new ImageStoringException();
			}
		});
		
		postRepository.save(post);
	}
	
	@Override
	public GetPostResponse getPost(long id) {
		GetPostResponse response = new GetPostResponse();
		response.setImageIds(new ArrayList<>());
		
		Post domainObject = postRepository.findOne(id);
		
		if(domainObject != null) {
			response.setTitle(domainObject.getTitle());
			response.setDescription(domainObject.getDescription());
			response.setPriceMin(domainObject.getPriceMin());
			response.setPriceMax(domainObject.getPriceMax());
			response.setCreationDateTime(domainObject.getCreationDateTime());
			response.setCategoryName(domainObject.getCategory().getName());
			
			response.setCity(domainObject.getLocation().getCity());
			response.setPostalCode(domainObject.getLocation().getPostalCode());
			response.setLatitude(domainObject.getLocation().getLatitude());
			response.setLongitude(domainObject.getLocation().getLongitude());
			
			domainObject.getImages().forEach(image -> response.getImageIds().add(image.getId()));
			
			if(userHasAuthority("ROLE_USER")) {
				response.setName(domainObject.getName());
				response.setPhone(domainObject.getPhone());
			}
		}
		
		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListPostsResponse listPosts(ListPostsRequest request) {
		ListPostsResponse response = new ListPostsResponse();
		response.setPosts(new ArrayList<>());
		
		Predicate predicate = ExpressionUtils
				.allOf(PostPredicates.titleLike(request.getTitle()),
						PostPredicates.cityLike(request.getCity()),
						PostPredicates.priceBetween(request.getPriceMin(),	request.getPriceMax()),
						PostPredicates.categoryEquals(request.getCategory()),
						PostPredicates.dateBetween(request.getStartDate(), request.getEndDate()));
		
		List<Post> postDomains = postRepository.findAll(predicate, new PageRequest(request.getPage() - 1, request.getPageSize(), Direction.DESC, "creationDateTime")).getContent();
		postDomains
			.stream()
			.forEach(domainObject -> {
				String locationString = new StringBuilder()
						.append(domainObject.getLocation().getCity())
						.append(",")
						.append("%20") // space
						.append(domainObject.getLocation().getPostalCode())
						.append("%20") // space
						.append("Hungary")
						.toString();
				
				String mapUrl = new StringBuilder()
						.append("http://maps.googleapis.com/maps/api/staticmap?center=")
						.append(locationString)
						.append("&size=200x200")
						.append("&zoom=9")
						.append("&markers=")
						.append(locationString)
						.append("key=")
						.append(ServiceFinder.API_KEY)
						.toString();
				
				ListPostData postBean = new ListPostData();
				postBean.setId(domainObject.getId());
				postBean.setTitle(domainObject.getTitle());
				postBean.setPriceMin(domainObject.getPriceMin());
				postBean.setPriceMax(domainObject.getPriceMax());
				postBean.setCreationDateTime(domainObject.getCreationDateTime());
				postBean.setCity(domainObject.getLocation().getCity());
				postBean.setMapUrl(mapUrl);
				postBean.setCategoryName(domainObject.getCategory().getName());
				
				response.getPosts().add(postBean);	
			});
		
		response.setTotalPosts(postRepository.count(predicate));
		
		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GetCategoriesResponse getCategories() {
		GetCategoriesResponse response = new GetCategoriesResponse();
		response.setCategories(new ArrayList<>());
		
		categoryRepository.findAll().forEach(c -> {
			GetCategoriesData getCategoriesData = new GetCategoriesData();
			getCategoriesData.setId(c.getId());
			getCategoriesData.setName(c.getName());
			
			response.getCategories().add(getCategoriesData);
		});
		
		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String checkAddress(String postalCode) throws LocationFindingException {
		return findOrCreateLocation(postalCode).getCity();
	}

	private Location findOrCreateLocation(String postalCode) throws LocationFindingException {
		Location location = null;
		
		try {
			// Try to fetch from db.
			location = locationRepository.findByPostalCode(postalCode).get(0);	
		} catch(IndexOutOfBoundsException e) {
			// Not found in db, we create it.
			location = createLocationWithGeocode(postalCode);
			locationRepository.save(location);
		}
		
		return location;
	}
	
	private Location createLocationWithGeocode(String postalCode) throws LocationFindingException {
		Location result = new Location();
		
		if(!postalCodePattern.matcher(postalCode).matches()) {
			throw new PostalCodeFormatException();
		}
		
		// Getting location for postal code, coordinates not very accurate
		GeocodingResult[] geocodingResultList;
		try {
			geocodingResultList = GeocodingApi.geocode(geoApiContext, postalCode + " Hungary").await();
		} catch (Exception e) {
			throw new RuntimeException("Ismeretlen hiba történt!");
		}
		
		if(geocodingResultList.length != 1) {
			throw new CityNotFoundException();
		} else {
			GeocodingResult geocodingResult = geocodingResultList[0];
		
			// Setting the name of the city.
			AddressComponent cityAddressComponent = extractAddressComponent(geocodingResult, AddressComponentType.LOCALITY);
			result.setCity(cityAddressComponent.longName);
			
			// Setting the postal code of the city.
			AddressComponent postalCodeAddressComponent = extractAddressComponent(geocodingResult, AddressComponentType.POSTAL_CODE);
			result.setPostalCode(postalCodeAddressComponent.longName);
			
			// Getting coordinates for full address, more accurate
			try {
				geocodingResultList = GeocodingApi.geocode(geoApiContext, cityAddressComponent.longName + ", " + postalCode + " Hungary").await();
			} catch (Exception e) {
				throw new RuntimeException("Ismeretlen hiba történt!");
			}
			
			if(geocodingResultList.length != 1) {
				throw new CityNotFoundException();
			} else {
				geocodingResult = geocodingResultList[0];
				
				result.setLatitude(geocodingResult.geometry.location.lat);
				result.setLongitude(geocodingResult.geometry.location.lng);
			}
		}
		
		return result;
	}

	private AddressComponent extractAddressComponent(GeocodingResult geocodingResult, AddressComponentType componentType) throws CityNotFoundException {
		AddressComponent cityAddressComponent = Arrays.asList(geocodingResult.addressComponents).stream()
			.filter(ac -> Arrays.asList(ac.types).contains(componentType))
			.findFirst()
			.orElseThrow(() -> new CityNotFoundException());
		return cityAddressComponent;
	}
	
	private boolean userHasAuthority(String authority) {
		Object loggedInPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(loggedInPrincipal instanceof UserDetails) {
			// logged in user
			return ((UserDetails)loggedInPrincipal).getAuthorities().stream().filter(a -> a.getAuthority().equals(authority)).count() > 0;	
		} else {
			// anonymous user
			return false;
		}
	}

}
