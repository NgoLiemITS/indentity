package com.devteria.identityservice.repository;

import com.devteria.identityservice.entity.ElectricityUsageHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ElectricityUsageHistoryRepository extends JpaRepository<ElectricityUsageHistory, String> {
    boolean existsByHomePhoneNumber(String homePhoneNumber);
    boolean existsByDate(Date date);
    //    Optional<ElectricityUsageHistory> findByPhoneNumber(String homePhoneNumber);
    List<ElectricityUsageHistory> findByHomePhoneNumber(String homePhoneNumber);
}
