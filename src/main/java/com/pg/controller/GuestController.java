package com.pg.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pg.dto.ApiResponse;
import com.pg.dto.GuestDto;
import com.pg.service.GuestService;
import com.pg.util.SuccessResponseUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/guests")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

  

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GuestDto>> getGuestById(@PathVariable String id) {
        GuestDto guest = guestService.getGuestById(id);
        return ResponseEntity.ok(SuccessResponseUtil.success("Guest fetched successfully", guest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GuestDto>> updateGuest(
            @PathVariable String id,
            @Valid @RequestBody GuestDto guestDto) {

        GuestDto dto = guestService.updateGuest(id, guestDto);
        return ResponseEntity.ok(SuccessResponseUtil.success("Guest updated successfully", dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteGuest(@PathVariable String id) {
        guestService.deleteGuest(id);
        return ResponseEntity.ok(SuccessResponseUtil.success("Guest deleted successfully"));
    }
}
