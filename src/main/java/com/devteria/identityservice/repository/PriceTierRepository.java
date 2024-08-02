package com.devteria.identityservice.repository;

import com.devteria.identityservice.entity.PriceTier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceTierRepository extends JpaRepository<PriceTier, Long> {
    List<PriceTier> findAllByOrderByKwhAsc();
}
