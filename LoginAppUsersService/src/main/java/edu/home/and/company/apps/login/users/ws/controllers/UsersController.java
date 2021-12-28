package edu.home.and.company.apps.login.users.ws.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.home.and.company.apps.login.users.model.UserModel;
import edu.home.and.company.apps.login.users.service.UserService;
import edu.home.and.company.apps.login.users.ws.dto.CreateUserRequestDto;
import edu.home.and.company.apps.login.users.ws.dto.CreateUserResponseDto;
import edu.home.and.company.apps.login.users.ws.dto.UserResponseDto;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private Environment env;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/status/check")
	public String status()
	{
		return "working on port : " + env.getProperty("local.server.port") + " with token " + env.getProperty("token.secret");
	}
	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
				 produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<CreateUserResponseDto> createUser(@Valid @RequestBody CreateUserRequestDto createUserDto)
	{
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserModel userModel = modelMapper.map(createUserDto, UserModel.class);
		UserModel savedUserModel = userService.createUser(userModel);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(savedUserModel, CreateUserResponseDto.class));
	}
	
	@GetMapping(value = "/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserResponseDto> getUser(@PathVariable String userId)
	{
		UserModel savedUserModel = userService.getUserByUserId(userId);
		ModelMapper modelMapper = new ModelMapper();
		
		return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(savedUserModel, UserResponseDto .class));
	}
}
