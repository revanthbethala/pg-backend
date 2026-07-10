package com.pg.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pg.dto.DashboardDto;
import com.pg.entity.BranchEntity;
import com.pg.entity.GuestEntity;
import com.pg.entity.RoomEntity;
import com.pg.entity.UserEntity;
import com.pg.exception.ResourceNotFoundException;
import com.pg.repository.BranchRepository;
import com.pg.repository.GuestRepository;
import com.pg.repository.RoomRepository;
import com.pg.repository.UserRepository;

@Service
public class DashboardService {

    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;

    public DashboardService(
            UserRepository userRepository,
            BranchRepository branchRepository,
            RoomRepository roomRepository,
            GuestRepository guestRepository) {

        this.userRepository = userRepository;
        this.branchRepository = branchRepository;
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
    }

    public DashboardDto getDashboard(String userId) {

        DashboardDto dto = new DashboardDto();

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        List<BranchEntity> branches =
                branchRepository.findByUser_Id(user.getId());

        List<RoomEntity> rooms = new ArrayList<>();
        List<GuestEntity> guests = new ArrayList<>();

        for (BranchEntity branch : branches) {
            List<RoomEntity> branchRooms =
                    roomRepository.findByBranch_Id(branch.getId());

            rooms.addAll(branchRooms);

            for (RoomEntity room : branchRooms) {
                guests.addAll(
                        guestRepository.findByRoom_id(room.getId())
                );
            }
        }

        dto.setBranches(branches.size());
        dto.setRooms(rooms.size());
        dto.setGuests(guests.size());

        LocalDate today = LocalDate.now();

        long guestsThisMonth = guests.stream()
                .filter(g -> g.getJoiningDate() != null)
                .filter(g ->
                        g.getJoiningDate().getYear() == today.getYear()
                                && g.getJoiningDate().getMonth() == today.getMonth())
                .count();

        dto.setGuestsThisMonth(guestsThisMonth);

        double totalRent = 0;

        for (GuestEntity guest : guests) {

            RoomEntity room = rooms.stream()
                    .filter(r -> r.getId().equals(guest.getRoom().getId()))
                    .findFirst()
                    .orElse(null);

            if (room != null) {
                totalRent += room.getRent();
            }
        }

        dto.setAverageMonthlyRent(
                guests.isEmpty() ? 0 : totalRent / guests.size()
        );

        long maintenanceRooms = rooms.stream()
                .filter(RoomEntity::isMaintainance)
                .count();

        dto.setRoomsUnderMaintenance(maintenanceRooms);

        return dto;
    }
}