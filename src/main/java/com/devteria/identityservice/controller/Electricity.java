package com.devteria.identityservice.controller;

import com.devteria.identityservice.dto.request.ApiResponse;
import com.devteria.identityservice.dto.request.ElectricityUsageRequest;
import com.devteria.identityservice.dto.request.UserCreationRequest;
import com.devteria.identityservice.dto.response.UserResponse;
import com.devteria.identityservice.entity.ElectricityUsageHistory;
import com.devteria.identityservice.entity.PriceTier;
import com.devteria.identityservice.service.ElectricityUsageService;
import com.devteria.identityservice.service.PriceTierService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/electricity")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Electricity {
    PriceTierService priceTierService;

    ElectricityUsageService electricityUsageService;

    @GetMapping("/price-tiers")
    ApiResponse<List<PriceTier>> getAllPriceTiers() {
        return ApiResponse.<List<PriceTier>>builder()
                .result(priceTierService.getAllPriceTiers())
                .build();
    }


    @PostMapping("/electricity-usage/record")
    ApiResponse<ElectricityUsageHistory> recordUsage(@RequestBody @Valid ElectricityUsageRequest request) {
        return ApiResponse.<ElectricityUsageHistory>builder()
                .result(electricityUsageService.recordUsage(request.getHomePhoneNumber(), request.getDate(), request.getKwh()))
                .build();
    }

    @GetMapping("/electricity-usage/history")
    ApiResponse<List<ElectricityUsageHistory>> getAllUsageHistory() {
        return ApiResponse.<List<ElectricityUsageHistory>>builder()
                .result(electricityUsageService.getAllUsageHistory())
                .build();
    }


    @GetMapping("/electricity-usage/history/{homePhoneNumber}")
    ApiResponse<List<ElectricityUsageHistory>> getUsageHistory(@PathVariable String homePhoneNumber) {
        return ApiResponse.<List<ElectricityUsageHistory>>builder()
                .result(electricityUsageService.getUsageHistory(homePhoneNumber))
                .build();
    }

}
