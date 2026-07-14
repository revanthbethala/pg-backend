package com.pg.dto;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Component
public class BranchDto {
	private String id;
	@NotBlank
	@Size(min = 4,message = "Min 4 chars needed")
	private String branchName;
	@NotBlank
	@Size(min = 6,message = "Min 6 chars needed")
	private String address;
	
    private Boolean isActive = true;
    private String userId;
    private java.util.List<RoomDto> rooms;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public java.util.List<RoomDto> getRooms() {
		return rooms;
	}
	public void setRooms(java.util.List<RoomDto> rooms) {
		this.rooms = rooms;
	}
	@Override
	public String toString() {
		return "BranchDto [id=" + id + ", branchName=" + branchName + ", address=" + address
				+ ", isActive=" + isActive + ", userId=" + userId + ", rooms=" + rooms + "]";
	}
    
}
