package edu.home.and.company.apps.login.users.model;

import java.io.Serializable;
import java.util.List;

import edu.home.and.company.apps.login.users.ws.dto.AlbumResponseDto;

public class UserModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 505033373443153442L;
	
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String encriptedPassowrd;
	private List<AlbumResponseDto> albums;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEncriptedPassowrd() {
		return encriptedPassowrd;
	}

	public void setEncriptedPassowrd(String encriptedPassowrd) {
		this.encriptedPassowrd = encriptedPassowrd;
	}

	public List<AlbumResponseDto> getAlbums() {
		return albums;
	}

	public void setAlbums(List<AlbumResponseDto> albums) {
		this.albums = albums;
	}
}
