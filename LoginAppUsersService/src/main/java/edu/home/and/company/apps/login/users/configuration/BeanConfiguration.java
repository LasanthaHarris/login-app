package edu.home.and.company.apps.login.users.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import edu.home.and.company.apps.login.users.exceptions.FeignErrorDecoder;
import feign.Logger;

@Configuration
public class BeanConfiguration {
	
	@Bean
	BCryptPasswordEncoder getBCryptPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@LoadBalanced
	RestTemplate getRestTemplate()
	{
		return new RestTemplate();
	}
	
	@Bean
	public FeignErrorDecoder getFeignErrorDecoder()
	{
		return new FeignErrorDecoder();
	}
	
	@Bean
	public Logger.Level getFeignLogLevel()
	{
		return Logger.Level.FULL;
	}
	

}
