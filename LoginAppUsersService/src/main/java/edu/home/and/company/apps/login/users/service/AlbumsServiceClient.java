package edu.home.and.company.apps.login.users.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import edu.home.and.company.apps.login.users.ws.dto.AlbumResponseDto;

@FeignClient(name = "albums-ws", fallbackFactory =  AlbumServiceFallBackFactory.class)
public interface AlbumsServiceClient {
	
	@GetMapping("/users/{id}/albums")
	public List<AlbumResponseDto> getAlbums(@PathVariable String id);

}
