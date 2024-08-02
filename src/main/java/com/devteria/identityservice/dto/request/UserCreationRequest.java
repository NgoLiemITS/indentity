package com.devteria.identityservice.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.devteria.identityservice.validator.DobConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {

    @NotBlank
    @Size(min = 4, message = "USERNAME_INVALID")
    String username;

    @NotBlank
    @Size(min = 6, message = "INVALID_PASSWORD")
    String password;

    @NotBlank
    @Pattern(regexp = "\\d{10}", message = "PHONE_NUMBER_INVALID")
    String phoneNumber;

    String firstName;
    String lastName;

    @DobConstraint(min = 10, message = "INVALID_DOB")
    LocalDate dob;
}
