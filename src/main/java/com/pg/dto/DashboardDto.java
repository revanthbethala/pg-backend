package com.pg.dto;

public class DashboardDto {

    private long branches;
    private long rooms;
    private long guests;
    private long guestsThisMonth;
    private double averageMonthlyRent;
    private long roomsUnderMaintenance;

    public DashboardDto() {
    }

    public long getBranches() {
        return branches;
    }

    public void setBranches(long branches) {
        this.branches = branches;
    }

    public long getRooms() {
        return rooms;
    }

    public void setRooms(long rooms) {
        this.rooms = rooms;
    }

    public long getGuests() {
        return guests;
    }

    public void setGuests(long guests) {
        this.guests = guests;
    }

    public long getGuestsThisMonth() {
        return guestsThisMonth;
    }

    public void setGuestsThisMonth(long guestsThisMonth) {
        this.guestsThisMonth = guestsThisMonth;
    }

    public double getAverageMonthlyRent() {
        return averageMonthlyRent;
    }

    public void setAverageMonthlyRent(double averageMonthlyRent) {
        this.averageMonthlyRent = averageMonthlyRent;
    }

    public long getRoomsUnderMaintenance() {
        return roomsUnderMaintenance;
    }

    public void setRoomsUnderMaintenance(long roomsUnderMaintenance) {
        this.roomsUnderMaintenance = roomsUnderMaintenance;
    }
}