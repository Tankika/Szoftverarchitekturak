package hu.bme.archi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PathNaturalizer {
	
	@RequestMapping(value = "/{[path:[^\\.]*}")
	public String redirect() {
		return "forward:/";
	}
}
