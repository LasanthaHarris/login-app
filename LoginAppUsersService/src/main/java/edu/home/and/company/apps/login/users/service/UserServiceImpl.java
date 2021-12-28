package edu.home.and.company.apps.login.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.home.and.company.apps.login.users.entity.UserEntity;
import edu.home.and.company.apps.login.users.model.UserModel;
import edu.home.and.company.apps.login.users.repository.UserRepository;
import edu.home.and.company.apps.login.users.ws.dto.AlbumResponseDto;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private RestTemplate restTemplate;
	private Environment env;
	private AlbumsServiceClient albumsService;
	
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, 
			BCryptPasswordEncoder bCryptPasswordEncoder,
			RestTemplate restTemplate,
			Environment env,
			AlbumsServiceClient albumsService) 
	{

		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.restTemplate = restTemplate;
		this.env = env;
		this.albumsService = albumsService;
	}

	@Override
	public UserModel createUser(UserModel userModel) {

		userModel.setUserId(UUID.randomUUID().toString());
		userModel.setEncriptedPassowrd(bCryptPasswordEncoder.encode(userModel.getPassword()));

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserEntity userEntity = mapper.map(userModel, UserEntity.class);

		UserEntity savedEntity = userRepository.save(userEntity);

		return mapper.map(savedEntity, UserModel.class);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		UserEntity userEntity = userRepository.findByEmail(userName);

		if (userEntity == null) {
			throw new UsernameNotFoundException(userName);
		}
		return new User(userEntity.getEmail(), userEntity.getEncriptedPassowrd(), true, true, true, true,
				new ArrayList<>());
	}

	@Override
	public UserModel getUserDetailsByEmail(String email) { // email == userName

		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null) {
			throw new UsernameNotFoundException(email);
		}
		return new ModelMapper().map(userEntity, UserModel.class);
	}
	
	@Override
	public UserModel getUserByUserId(String userId) {

		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity == null) {
			throw new UsernameNotFoundException(userId);
		}
		UserModel user = new ModelMapper().map(userEntity, UserModel.class);

		logger.info("Before calling AlbumService#getAlbums()");
		List<AlbumResponseDto> albumDtoList = albumsService.getAlbums(userId);
		logger.info("After calling AlbumService#getAlbums()");
		user.setAlbums(albumDtoList);

		return user;
	}

	/* 
	 * ------Inter Service communication with RestTemplate-----
	 * @Override public UserModel getUserByUserId(String userId) {
	 * 
	 * UserEntity userEntity = userRepository.findByUserId(userId);
	 * 
	 * if (userEntity == null) { throw new UsernameNotFoundException(userId); }
	 * UserModel user = new ModelMapper().map(userEntity, UserModel.class);
	 * 
	 * String url = String.format(env.getProperty("albums.url"), userId);
	 * //http://album-ws/users/{userId/albums}
	 * 
	 * ResponseEntity<List<AlbumResponseDto>> albumResponseDtoList =
	 * restTemplate.exchange(url, HttpMethod.GET, null, new
	 * ParameterizedTypeReference<List<AlbumResponseDto>>() { });
	 * 
	 * List<AlbumResponseDto> albumDtoList = albumResponseDtoList.getBody();
	 * user.setAlbums(albumDtoList);
	 * 
	 * return user; }
	 */
	
	

}
