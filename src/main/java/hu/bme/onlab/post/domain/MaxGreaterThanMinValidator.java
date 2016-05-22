package hu.bme.onlab.post.domain;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MaxGreaterThanMinValidator implements ConstraintValidator<MaxGreaterThanMin, Post> {

	@Override
	public void initialize(MaxGreaterThanMin constraintAnnotation) {
	}

	@Override
	public boolean isValid(Post value, ConstraintValidatorContext context) {
		if(value.getPriceMin() <= value.getPriceMax()) {
			return true;
		} else {
			return false;
		}
	}

}
