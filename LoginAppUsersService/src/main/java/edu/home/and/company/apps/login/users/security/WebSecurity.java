package edu.home.and.company.apps.login.users.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import edu.home.and.company.apps.login.users.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	private Environment environment;
	private UserService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public WebSecurity(Environment environment, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		
		this.environment = environment;
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		System.out.println("IP Address is: " + environment.getProperty("gateway.ip"));
		
		http.csrf().disable();
//		http.authorizeRequests().antMatchers("/**").hasIpAddress(environment.getProperty("gateway.ip"));
//		http.authorizeRequests().antMatchers("/**").hasIpAddress("192.168.1.3");
		http.authorizeRequests().antMatchers("/users/**").permitAll()
		.and()
		.addFilter(getAuthenticationFilter());
		http.headers().frameOptions().disable();
	}

	private AuthenticationFilter getAuthenticationFilter() throws Exception {
		
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, environment, authenticationManager());
		//authenticationFilter.setAuthenticationManager(authenticationManager());
		authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
		return authenticationFilter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	

}