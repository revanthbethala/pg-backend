package com.pg.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "branches")
public class BranchEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	@Column(nullable = false)
	private String branchName;
	private String address;
	private Boolean isActive = true;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;
	@OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true)
	private java.util.List<RoomEntity> rooms;

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

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public java.util.List<RoomEntity> getRooms() {
		return rooms;
	}

	public void setRooms(java.util.List<RoomEntity> rooms) {
		this.rooms = rooms;
	}

	@Override
	public String toString() {
		return "BranchEntity [id=" + id + ", branchName=" + branchName + ", address=" + address + ", city=" 
				+ ", isActive=" + isActive + ", user=" + user + "]";
	}
}
