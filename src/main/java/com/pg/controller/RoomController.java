package com.pg.controller;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pg.dto.GuestDto;
import com.pg.dto.RoomDto;
import com.pg.service.GuestService;
import com.pg.service.RoomService;
import com.pg.util.ErrorResponseUtil;
import com.pg.util.SuccessResponseUtil;
import com.pg.util.ValidationUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/rooms")
public class RoomController {

    private RoomService roomService;
    private GuestService guestService;

    public RoomController(RoomService roomService,GuestService guestService) {
        this.roomService = roomService;
        this.guestService = guestService;
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<Map<String, Object>> getRoomById(
            @PathVariable String id) {

        RoomDto room = roomService.getRoomById(id);

        return ResponseEntity.ok(
                SuccessResponseUtil.success("Room found", room));
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateRoom(
            @PathVariable String id,
            @Valid @RequestBody RoomDto roomDto,
            BindingResult result) {

        if (result.hasErrors()) {

            Map<String, String> validationErrors =
                    ValidationUtil.getValidationErrors(result);

            if (!validationErrors.isEmpty()) {

                Map<String, Object> error =
                        ErrorResponseUtil.buildError(
                                HttpStatus.BAD_REQUEST,
                                "Validation failed");

                error.put("errors", validationErrors);

                return ResponseEntity.badRequest().body(error);
            }
        }

        RoomDto dto = roomService.updateRoom(id, roomDto);

        return ResponseEntity.ok(
                SuccessResponseUtil.success(
                        "Room updated successfully", dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteRoom(
            @PathVariable String id) {

        roomService.deleteRoom(id);

        return ResponseEntity.ok(
                SuccessResponseUtil.success(
                        "Room deleted successfully"));
    }
    
    @GetMapping("/{roomId}/guests")
    public ResponseEntity<Map<String, Object>> getAllGuests(@PathVariable String roomId) {
        List<GuestDto> guests = guestService.getAllGuests(roomId);
        return ResponseEntity.ok(SuccessResponseUtil.success("Guests fetched successfully", guests));
    }
    

    @PostMapping("/{roomId}/guests")
    public ResponseEntity<Map<String, Object>> createGuest(
            @PathVariable String roomId,
            @Valid @RequestBody GuestDto guestDto,
            BindingResult result) {

        if (result.hasErrors()) {

            Map<String, String> validationErrors =
                    ValidationUtil.getValidationErrors(result);

            if (!validationErrors.isEmpty()) {

                Map<String, Object> error =
                        ErrorResponseUtil.buildError(
                                HttpStatus.BAD_REQUEST,
                                "Validation failed");

                error.put("errors", validationErrors);

                return ResponseEntity.badRequest().body(error);
            }
        }

        GuestDto dto = guestService.createGuest(guestDto,roomId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponseUtil.success(
                        "Guest created successfully", dto));
    }


}