package com.devteria.identityservice.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.devteria.identityservice.validator.DobConstraint;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @NotBlank
    @Size(min = 6, message = "INVALID_PASSWORD")
    String password;

    String firstName;
    String lastName;

    @NotBlank
    @Pattern(regexp = "\\d{10}", message = "PHONE_NUMBER_INVALID")
    String phoneNumber;
    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;

}
