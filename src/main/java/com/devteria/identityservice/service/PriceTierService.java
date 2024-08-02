package com.devteria.identityservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import com.devteria.identityservice.entity.PriceTier;
import com.devteria.identityservice.repository.PriceTierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PriceTierService {
    PriceTierRepository priceTierRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public List<PriceTier> getAllPriceTiers() {
        return priceTierRepository.findAll();
    }
}
