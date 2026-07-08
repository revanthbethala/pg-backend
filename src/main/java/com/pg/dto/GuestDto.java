package com.pg.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

public class GuestDto {

    private String id;

    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Phone no. is required")
    @Pattern(regexp = "^[0-9]\\d{9}$", message = "Invalid phone number")
    private String phone;
    @NotBlank(message = "Aadhaar is required")

    @Pattern(regexp = "^\\d{12}$", message = "Aadhaar must contain 12 digits")
    private String aadhaar;

    private String profilePic;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Joining date is required")
    @PastOrPresent(message = "Joining date cannot be in the future")
//    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate joiningDate;

    private String roomId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAadhaar() {
		return aadhaar;
	}

	public void setAadhaar(String aadhaar) {
		this.aadhaar = aadhaar;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDate getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Override
	public String toString() {
		return "GuestDto [id=" + id + ", name=" + name + ", phone=" + phone + ", aadhaar=" + aadhaar + ", profilePic="
				+ profilePic + ", address=" + address + ", joiningDate=" + joiningDate + ", roomId=" + roomId + "]";
	}


}
