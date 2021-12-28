package edu.home.and.company.apps.login.users.repository;

import org.springframework.data.repository.CrudRepository;

import edu.home.and.company.apps.login.users.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
	
	UserEntity findByEmail(String email);

	UserEntity findByUserId(String userId);

}
