package hu.bme.archi.post.domain;

import java.util.Calendar;

import com.mysema.query.types.ExpressionUtils;
import com.mysema.query.types.Predicate;

//@Deprecated
public class PostPredicates {

	public static Predicate titleLike(String title) {
		if(title != null) {
			return QPost.post.title.like("%" + title + "%");
		} else {
			return null;
		}
	}

	public static Predicate cityLike(String city) {
		if(city != null) {
			return QPost.post.location.city.like("%" + city + "%");
		} else {
			return null;
		}
	}

	public static Predicate priceBetween(Integer priceLow, Integer priceHigh) {
		Predicate expression1 = null, expression2 = null;
		
		if(priceLow != null) {
			expression1 = QPost.post.priceMax.goe(priceLow).or(QPost.post.priceMin.goe(priceLow));
		} 
		if(priceHigh != null) {
			expression2 = QPost.post.priceMin.loe(priceHigh).or(QPost.post.priceMax.loe(priceHigh));
		}
		
		return ExpressionUtils.allOf(expression1, expression2);
	}

	public static Predicate categoryEquals(Long id) {
		if(id != null) {
			return QPost.post.category.id.eq(id);
		} else {
			return null;
		}
	}
	
	public static Predicate dateBetween(Calendar startDate, Calendar endDate) {
		Predicate expression1 = null, expression2 = null;
		
		if(startDate != null) {
			expression1 = QPost.post.creationDateTime.goe(startDate);
		} 
		if(endDate != null) {
			expression2 = QPost.post.creationDateTime.loe(endDate);
		}
		
		return ExpressionUtils.allOf(expression1, expression2);
	}
	
}
