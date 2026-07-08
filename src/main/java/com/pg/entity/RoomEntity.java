package com.pg.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
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
@Table(name = "rooms")
public class RoomEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private int roomNumber;
	private int capacity;
	private double rent;
	private boolean maintainance = false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branchId")
	private BranchEntity branch;

	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<GuestEntity> guests = new ArrayList<>();

	public List<GuestEntity> getGuests() {
		return guests;
	}

	public void setGuests(List<GuestEntity> guests) {
		this.guests = guests;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public double getRent() {
		return rent;
	}

	public void setRent(double rent) {
		this.rent = rent;
	}

	public boolean isMaintainance() {
		return maintainance;
	}

	public void setMaintainance(boolean maintainance) {
		this.maintainance = maintainance;
	}

	public BranchEntity getBranchId() {
		return branch;
	}

	public void setBranchId(BranchEntity branch) {
		this.branch = branch;
	}

	@Override
	public String toString() {
		return "RoomEntity [id=" + id + ", roomNumber=" + roomNumber + ", capacity=" + capacity + ", rent=" + rent
				+ ", maintainance=" + maintainance + "]";
	}

}
