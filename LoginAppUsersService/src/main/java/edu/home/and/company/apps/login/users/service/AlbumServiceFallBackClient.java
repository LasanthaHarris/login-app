package edu.home.and.company.apps.login.users.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.home.and.company.apps.login.users.ws.dto.AlbumResponseDto;
import feign.FeignException;

public class AlbumServiceFallBackClient implements AlbumsServiceClient {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Throwable cause;

	public AlbumServiceFallBackClient(Throwable cause) {

		this.cause = cause;

	}

	@Override
	public List<AlbumResponseDto> getAlbums(String userId) {

		if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
			logger.error("404 error took place when getAlbums() was called with user id: " + userId + ". Error message: "
					+ cause.getLocalizedMessage());
		} else {
			logger.error("Un-hanlded error took place: " + cause.getLocalizedMessage());
		}

		return new ArrayList<>();
	}
}
