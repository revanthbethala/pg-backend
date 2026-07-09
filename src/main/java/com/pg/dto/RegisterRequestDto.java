package com.pg.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequestDto {
	@NotBlank(message = "Email cant be empty")
	@Email
	private String email;
	@NotBlank(message = "Name cant be empty")
	private String name;
	@NotBlank(message = "Password cant be empty")
	@Size(min = 8, message = "Password should be min of 8 chars")
	private String password;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "RegisterRequestDto [email=" + email + ", name=" + name + ", password=" + password + "]";
	}
	
}
