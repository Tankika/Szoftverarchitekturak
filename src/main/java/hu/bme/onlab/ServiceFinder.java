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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@EnableJpaRepositories
@SpringBootApplication
public class ServiceFinder {	
	
	public static final String API_KEY;
	
	static {
		API_KEY = System.getenv("google.key");
		
		if(API_KEY == null) {
			throw new IllegalStateException("Google api key is not set in system properties");
		}
	}

	public static void main(String[] args) {		
		SpringApplication.run(ServiceFinder.class, args);
	}

	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		
		@Autowired
		private DataSource dataSource;

		@Autowired
		private PasswordEncoder passwordEncoder;
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			AuthenticationEntryPoint authenticationEntryPoint = new CustomAuthenticationEntryPoint();
			
			http
				.httpBasic().authenticationEntryPoint(authenticationEntryPoint)
			.and()
				.authorizeRequests()
					.antMatchers("/", "/user/signup", "/user/checkEmail", "/user/user", "post/getCategories", "/post/listPosts", "/post/**", "/post/downloadImage/**", "/404", "/fonts/**", "/font-awesome/**")
						.permitAll()
					.anyRequest()
						.authenticated()
			.and()
				.logout()
					.logoutSuccessUrl("/")
			.and()
				.addFilterAfter(new CsrfHeaderFilter(), SessionManagementFilter.class)
				.csrf()
				.csrfTokenRepository(csrfTokenRepository())
			.and()
				.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
		}
		
		private CsrfTokenRepository csrfTokenRepository() {
			HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
			repository.setHeaderName("X-XSRF-TOKEN");
			return repository;
		}
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth
				.jdbcAuthentication()
				.dataSource(this.dataSource)
				.passwordEncoder(passwordEncoder)
				.authoritiesByUsernameQuery(getAuthoritiesByUsernameQuery())
				.usersByUsernameQuery(getUsersByUsernameQuery());
		}
		
		private static String getAuthoritiesByUsernameQuery() {
			return "select " 
				+		"users.email, auth.authority "
				+	"from "
				+		"authority auth, users, user_auth " 
				+ 	"where "
				+		"auth.ID = user_auth.AUTH_ID "
				+		"and user_auth.USER_ID = users.ID "
				+		"and users.email = ?";
		}
		
		private static String getUsersByUsernameQuery() {
			return "select "
				+		"users.email, users.password, users.enabled "
				+	"from "
				+		"users "
				+	"where "
				+		"users.email = ?";
		}		
		
		public static class CsrfHeaderFilter extends OncePerRequestFilter {
			@Override
			protected void doFilterInternal(HttpServletRequest request,
					HttpServletResponse response, FilterChain filterChain)
					throws ServletException, IOException {
				CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
						.getName());
				if (csrf != null) {
					Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
					String token = csrf.getToken();
					if (cookie == null || token != null && !token.equals(cookie.getValue())) {
						cookie = new Cookie("XSRF-TOKEN", token);
						cookie.setPath("/");
						response.addCookie(cookie);
					}
				}
				filterChain.doFilter(request, response);
			}
		}
		
		private static class CustomAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
			
			public CustomAuthenticationEntryPoint() {
				this.setRealmName("ServiceFinder");
			}
			
			@Override
			public void commence(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException authException) throws IOException, ServletException {
				if(authException instanceof BadCredentialsException || authException instanceof InsufficientAuthenticationException) {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
				} else {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, authException.getMessage());					
				}
			}
		}
		
	}
}
