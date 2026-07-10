package com.pg.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pg.dto.ApiResponse;
import com.pg.dto.DashboardDto;
import com.pg.service.DashboardService;
import com.pg.util.SuccessResponseUtil;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<DashboardDto>> getDashboard(
            Authentication authentication) {

        DashboardDto dashboard =
                dashboardService.getDashboard(authentication.getName());

        return ResponseEntity.ok(
                SuccessResponseUtil.success(
                        "Dashboard fetched successfully",
                        dashboard
                )
        );
    }
}