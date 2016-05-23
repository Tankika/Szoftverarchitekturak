package hu.bme.onlab.post.service;

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

import hu.bme.onlab.ServiceFinder;
import hu.bme.onlab.post.bean.CityNotFoundException;
import hu.bme.onlab.post.bean.ListPostData;
import hu.bme.onlab.post.bean.ListPostsRequest;
import hu.bme.onlab.post.bean.ListPostsResponse;
import hu.bme.onlab.post.bean.LocationFindingException;
import hu.bme.onlab.post.bean.PostalCodeFormatException;
import hu.bme.onlab.post.bean.SendPostRequest;
import hu.bme.onlab.post.domain.Location;
import hu.bme.onlab.post.domain.Post;
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
	
	private GeoApiContext geoApiContext;
	
	@Autowired
	public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, LocationRepository locationRepository, GeoApiContext geoApiContext) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.locationRepository = locationRepository;
		this.geoApiContext = geoApiContext;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendPost(SendPostRequest request) throws LocationFindingException {

		UserDetails principal = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findByEmailIgnoreCase(principal.getUsername()).get(0);
		
		Location location = findOrCreateLocation(request.getPostalCode());
		
		Post post = new Post();
		post.setTitle(request.getTitle());
		post.setDescription(request.getDescription());
		post.setPriceMin(request.getPriceMin());
		post.setPriceMax(request.getPriceMax());
		post.setName(request.getName());
		post.setPhone(request.getPhone());
		post.setCreationDateTime(Calendar.getInstance());
		post.setAdvertiser(user);
		post.setLocation(location);
		
		postRepository.save(post);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListPostsResponse listPosts(ListPostsRequest request) {
		ListPostsResponse response = new ListPostsResponse();
		response.setPosts(new ArrayList<>());
		
		List<Post> postDomains = postRepository.findAll(new PageRequest(request.getPage() - 1, request.getPageSize(), Direction.DESC, "creationDateTime")).getContent();
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
				postBean.setTitle(domainObject.getTitle());
				postBean.setPriceMin(domainObject.getPriceMin());
				postBean.setPriceMax(domainObject.getPriceMax());
				postBean.setCreationDateTime(domainObject.getCreationDateTime());
				postBean.setCity(domainObject.getLocation().getCity());
				postBean.setMapUrl(mapUrl);
				
				response.getPosts().add(postBean);	
			});
		
		response.setTotalPosts(postRepository.count());
		
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
			
			// Setting the post code of the city.
			AddressComponent postalCodeAddressComponent = extractAddressComponent(geocodingResult, AddressComponentType.POSTAL_CODE);
			result.setPostalCode(postalCodeAddressComponent.longName);
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
	
}
