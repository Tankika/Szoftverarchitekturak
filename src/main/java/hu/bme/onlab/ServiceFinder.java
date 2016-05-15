package hu.bme.onlab;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@SpringBootApplication
public class ServiceFinder {

	public static void main(String[] args) {
		SpringApplication.run(ServiceFinder.class, args);
	}

	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		
		@Autowired
		DataSource dataSource;
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.httpBasic()
			.and()
				.authorizeRequests().antMatchers("/index.html", "/home.html", "/login.html", "/user", "/").permitAll()
				.anyRequest().authenticated()
			.and()
				.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
				.csrf()
				.csrfTokenRepository(csrfTokenRepository());
		}
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.jdbcAuthentication().dataSource(this.dataSource);
		}
		
		private CsrfTokenRepository csrfTokenRepository() {
		  HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		  repository.setHeaderName("X-XSRF-TOKEN");
		  return repository;
		}
		
		public class CsrfHeaderFilter extends OncePerRequestFilter {
		  @Override
		  protected void doFilterInternal(HttpServletRequest request,
		      HttpServletResponse response, FilterChain filterChain)
		      throws ServletException, IOException {
		    CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
		        .getName());
		    if (csrf != null) {
		      Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
		      String token = csrf.getToken();
		      if (cookie==null || token!=null && !token.equals(cookie.getValue())) {
		        cookie = new Cookie("XSRF-TOKEN", token);
		        cookie.setPath("/");
		        response.addCookie(cookie);
		      }
		    }
		    filterChain.doFilter(request, response);
		  }
		}
	}
}
