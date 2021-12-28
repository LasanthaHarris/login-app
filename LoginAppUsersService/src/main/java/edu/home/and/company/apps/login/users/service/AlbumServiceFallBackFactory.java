package edu.home.and.company.apps.login.users.service;

import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;

@Component
public class AlbumServiceFallBackFactory implements FallbackFactory<AlbumServiceFallBackClient> {

	@Override
	public AlbumServiceFallBackClient create(Throwable cause) {
		
		return new AlbumServiceFallBackClient(cause);
	}
}
