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
import com.pg.service.GuestService;
import com.pg.util.ErrorResponseUtil;
import com.pg.util.SuccessResponseUtil;
import com.pg.util.ValidationUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/guests")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

  

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getGuestById(@PathVariable String id) {
        GuestDto guest = guestService.getGuestById(id);
        return ResponseEntity.ok(SuccessResponseUtil.success("Guest found", guest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateGuest(
            @PathVariable String id,
            @Valid @RequestBody GuestDto guestDto,
            BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> validationErrors = ValidationUtil.getValidationErrors(result);
            Map<String, Object> error = ErrorResponseUtil.buildError(HttpStatus.BAD_REQUEST, "Validation failed");
            error.put("errors", validationErrors);
            return ResponseEntity.badRequest().body(error);
        }

        GuestDto dto = guestService.updateGuest(id, guestDto);
        return ResponseEntity.ok(SuccessResponseUtil.success("Guest updated successfully", dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteGuest(@PathVariable String id) {
        guestService.deleteGuest(id);
        return ResponseEntity.ok(SuccessResponseUtil.success("Guest deleted successfully"));
    }
}
