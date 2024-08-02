package com.devteria.identityservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;

@Data
public class ElectricityUsageRequest {

    @NotBlank
    @Pattern(regexp = "\\d{10}", message = "PHONE_NUMBER_INVALID")
    String homePhoneNumber;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    Date date;

    @NotNull
    int kwh;

}
