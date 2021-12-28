package edu.home.and.company.apps.login.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import edu.home.and.company.apps.login.users.model.UserModel;

public interface UserService extends UserDetailsService{
	
	UserModel createUser(UserModel user);

	UserModel getUserDetailsByEmail(String userName);

	UserModel getUserByUserId(String userId);

}
