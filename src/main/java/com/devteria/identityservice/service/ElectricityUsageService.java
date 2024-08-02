package com.devteria.identityservice.service;

import com.devteria.identityservice.exception.AppException;
import com.devteria.identityservice.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import com.devteria.identityservice.entity.ElectricityUsageHistory;
import com.devteria.identityservice.entity.PriceTier;
import com.devteria.identityservice.repository.ElectricityUsageHistoryRepository;
import com.devteria.identityservice.repository.PriceTierRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ElectricityUsageService {

    final ElectricityUsageHistoryRepository historyRepository;
    final PriceTierRepository repository;

    @PreAuthorize("hasRole('ADMIN')")
    public ElectricityUsageHistory recordUsage(String homePhoneNumber, Date date, int kwh) {

        if ( historyRepository.existsByHomePhoneNumber(homePhoneNumber) && historyRepository.existsByDate(date) ) {
            throw new AppException(ErrorCode.USER_EXISTED);
//            throw new RuntimeException("Duplicate phone number");
        }

        List<PriceTier> priceTiers = repository.findAllByOrderByKwhAsc();
        double totalCost = calculateTotalCost(kwh, priceTiers);

        ElectricityUsageHistory history = new ElectricityUsageHistory();
        history.setHomePhoneNumber(homePhoneNumber);
        history.setDate(date);
        history.setKwh(kwh);
        history.setTotalCost(totalCost);

        return historyRepository.save(history);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ElectricityUsageHistory> getAllUsageHistory() {
        return historyRepository.findAll();
    }

    private double calculateTotalCost(int kwh, List<PriceTier> priceTiers) {
        double totalCost = 0;
        int remainingKwh = kwh;
        int previousKwh = 0;

        for (PriceTier tier : priceTiers) {
            if (remainingKwh <=0) break;

            int tierKwh = tier.getKwh() - previousKwh;
            if(remainingKwh <= tierKwh) {
                totalCost += remainingKwh * tier.getPrice();
                remainingKwh = 0;
            } else {
                totalCost += tierKwh * tier.getPrice();
                remainingKwh -= tierKwh;
            }
            previousKwh = tier.getKwh();
        }
        if (remainingKwh > 0) {
            double highestTierPrice = priceTiers.get(priceTiers.size() - 1).getPrice();
            totalCost += remainingKwh * highestTierPrice;
        }

        totalCost = Math.round(totalCost * 100.0) / 100.0;
        return totalCost;
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<ElectricityUsageHistory> getUsageHistory(String homePhoneNumber) {
        List<ElectricityUsageHistory> usageHistory = historyRepository.findByHomePhoneNumber(homePhoneNumber);
        if (usageHistory.isEmpty()) {
            throw new AppException(ErrorCode.DATA_NOT_EXIST);
//            throw new RuntimeException("No usage history found for phone number: " + homePhoneNumber);
        }
        return usageHistory;
    }

//    public ElectricityUsageHistory deleteUsageHistory(String phoneNumber) {
//        Optional<ElectricityUsageHistory> history = historyRepository.findByPhoneNumber(phoneNumber);
//        if (history.isPresent()) {
//            ElectricityUsageHistory usageHistory = history.get();
//            historyRepository.delete(usageHistory);
//            return usageHistory;
//        } else {
//            throw new AppException(ErrorCode.USER_NOT_EXISTED);
//        }
//    }


}

