package com.project.HPharmacy.dto;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRegistrationDto {
	@NotBlank(message = "*Please provide your full name")
    private String fullName;

    @NotBlank(message = "*Please provide your email")
    @Email(message = "*Please provide a valid email")
    private String email;

    @NotBlank(message = "*Please provide your phone number")
    @Pattern(regexp = "^\\d{10}$", message = "*Phone number must be 10 digits")
    private String phone;

    @NotBlank(message = "*Please provide a username")
    @Size(min = 5, max = 25, message = "*Your username '${validatedValue}' must be between {min} and {max} characters long")
    private String username;

    @NotBlank(message = "*Please provide a password")
    @Size(min = 5, max = 35, message = "*Your password '${validatedValue}' must be between {min} and {max} characters long")
    private String password;

    @Size(min = 1, max = 10,
			message = "*Your ROLE '${validatedValue}' must be between {min} and {max} characters long")
    private Set<String> roles;

    public UserRegistrationDto() {}

	public UserRegistrationDto(@NotBlank(message = "Please provide your full name") String fullName,
			@NotBlank(message = "Please provide your email") String email,
			@NotBlank(message = "Please provide your phone number") @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits") String phone,
			@NotBlank(message = "Please provide a username") @Size(min = 5, max = 25, message = "Your username '${validatedValue}' must be between {min} and {max} characters long") String username,
			@NotBlank(message = "Please provide a password") @Size(min = 5, max = 35, message = "Your password '${validatedValue}' must be between {min} and {max} characters long") String password,
			@Size(min = 1, max = 10, message = "Your ROLE '${validatedValue}' must be between {min} and {max} characters long") Set<String> roles) {
		super();
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserRegistrationDto [fullName=" + fullName + ", email=" + email + ", phone=" + phone + ", username="
				+ username + ", password=" + password + ", roles=" + roles + "]";
	}
    
}
