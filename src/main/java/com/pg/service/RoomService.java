package com.pg.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.pg.dto.RoomDto;
import com.pg.entity.BranchEntity;
import com.pg.entity.RoomEntity;
import com.pg.exception.DataAlreadyExistsException;
import com.pg.exception.ResourceNotFoundException;
import com.pg.mapper.RoomMapper;
import com.pg.repository.BranchRepository;
import com.pg.repository.RoomRepository;

@Service
public class RoomService {

    private RoomRepository roomRepository;
    private BranchRepository branchRepository;
    private RoomMapper roomMapper;

    public RoomService(RoomRepository roomRepository,
                       BranchRepository branchRepository,
                       RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.branchRepository = branchRepository;
        this.roomMapper = roomMapper;
    }

    public List<RoomDto> getAllRooms(String branchId) {

        BranchEntity branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));

        return roomRepository.findByBranchId(branch)
                .stream()
                .map(roomMapper::toDto)
                .toList();
    }

    public RoomDto getRoomById(String id) {

        RoomEntity room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        return roomMapper.toDto(room);
    }

    public RoomDto createRoom(String branchId, RoomDto dto) {

        if (roomRepository.existsByRoomNumber(dto.getRoomNumber())) {
            throw new DataAlreadyExistsException("Room number already exists");
        }

        BranchEntity branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));

        dto.setBranchId(branchId);
        RoomEntity room = roomMapper.toEntity(dto);
        room.setBranchId(branch);

        room = roomRepository.save(room);

        return roomMapper.toDto(room);
    }

    public RoomDto updateRoom(String id, RoomDto dto) {

        RoomEntity room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        if (roomRepository.existsByRoomNumberAndIdNot(dto.getRoomNumber(), id)) {
            throw new DataAlreadyExistsException("Room number already exists");
        }

        BranchEntity branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));

        roomMapper.updateEntity(dto, room);
        room.setBranchId(branch);

        room = roomRepository.save(room);

        return roomMapper.toDto(room);
    }

    public void deleteRoom(String id) {

        RoomEntity room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        roomRepository.delete(room);
    }

}
