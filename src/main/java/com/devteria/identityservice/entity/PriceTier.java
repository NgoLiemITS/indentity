package com.devteria.identityservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class PriceTier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    int kwh;
    double price;


}