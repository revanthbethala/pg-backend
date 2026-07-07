package com.pg.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pg.dto.GuestDto;
import com.pg.entity.GuestEntity;
import com.pg.entity.RoomEntity;
import com.pg.exception.DataAlreadyExistsException;
import com.pg.exception.ResourceNotFoundException;
import com.pg.mapper.GuestMapper;
import com.pg.repository.GuestRepository;
import com.pg.repository.RoomRepository;

@Service
public class GuestService {

    private final GuestRepository guestRepository;
    private final RoomRepository roomRepository;
    private final GuestMapper guestMapper;

    public GuestService(GuestRepository guestRepository,
                        RoomRepository roomRepository,
                        GuestMapper guestMapper) {
        this.guestRepository = guestRepository;
        this.roomRepository = roomRepository;
        this.guestMapper = guestMapper;
    }

    public GuestDto createGuest(GuestDto dto,String roomId) {

    	System.out.println("ROOM ID:"+roomId);
        RoomEntity room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        
        if (guestRepository.existsByAadhaar(dto.getAadhaar())) {
            throw new DataAlreadyExistsException("Aadhaar already exists");
        }

        dto.setRoomId(roomId);

        GuestEntity guest = guestMapper.toEntity(dto);
        guest.setRoom(room);

        return guestMapper.toDto(guestRepository.save(guest));
    }

    public List<GuestDto> getAllGuests(String roomId) {
        RoomEntity room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        return guestRepository.findAll()
                .stream()
                .map(guestMapper::toDto)
                .toList();
    }

    public GuestDto getGuestById(String id) {

        GuestEntity guest = guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found"));

        return guestMapper.toDto(guest);
    }

    public GuestDto updateGuest(String id, GuestDto dto) {

        GuestEntity guest = guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found"));

        if (!guest.getPhone().equals(dto.getPhone())
                && guestRepository.existsByPhone(dto.getPhone())) {
            throw new DataAlreadyExistsException("Phone already exists");
        }

        if (!guest.getAadhaar().equals(dto.getAadhaar())
                && guestRepository.existsByAadhaar(dto.getAadhaar())) {
            throw new DataAlreadyExistsException("Aadhaar already exists");
        }

        RoomEntity room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        guestMapper.updateEntity(dto, guest);
        guest.setRoom(room);

        return guestMapper.toDto(guestRepository.save(guest));
    }

    public void deleteGuest(String id) {

        GuestEntity guest = guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found"));

        guestRepository.delete(guest);
    }
}