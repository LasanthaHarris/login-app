package edu.home.and.company.apps.login.users.ws.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestDto {

	@NotNull(message = "First name canno't be null")
	@Size(min = 2, message = "First name canno't be less than two characters")
	private String firstName;

	@NotNull(message = "Last name canno't be null")
	@Size(min = 2, message = "Last name canno't be less than two characters")
	private String lastName;

	@NotNull(message = "Email canno't be null")
	@Email(message = "Invalied email")
	private String email;

	@NotNull(message = "Password canno't be null")
	@Size(min = 8, max = 16, message = "Password should be greater than 8 characters and less than 16 characters")
	private String password;

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

	public void setEmail(String emailId) {
		this.email = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
