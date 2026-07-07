package com.pg.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
	private String id;
	@NotBlank(message ="Email cant be empty")
	@Email
	private String email;
	@NotBlank(message = "Password cant be empty")
	@Size(min = 8,message = "Password should be min of 8 chars")
	private String password;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	@Override
	public String toString() {
		return "UserDto [id=" + id + ", email=" + email + ", password=" + password + "]";
	}
	
}
