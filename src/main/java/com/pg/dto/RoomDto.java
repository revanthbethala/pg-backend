package com.pg.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RoomDto {

    private String id;

    @NotNull(message = "Room number is required")
    @Min(value = 1, message = "Room number must be greater than 0")
    private Integer roomNumber;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    @NotNull(message = "Rent is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Rent must be greater than 0")
    private Double rent;

    private Boolean maintainance = false;

    private String branchId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Double getRent() {
		return rent;
	}

	public void setRent(Double rent) {
		this.rent = rent;
	}

	public Boolean getMaintainance() {
		return maintainance;
	}

	public void setMaintainance(Boolean maintainance) {
		this.maintainance = maintainance;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	@Override
	public String toString() {
		return "RoomDto [id=" + id + ", roomNumber=" + roomNumber + ", capacity=" + capacity + ", rent=" + rent
				+ ", maintainance=" + maintainance + ", branchId=" + branchId + "]";
	}

    
}