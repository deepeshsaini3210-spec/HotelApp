package com.grandstay.hotel.util.wrappers;

import java.time.LocalDateTime;
import java.util.List;


import com.grandstay.hotel.model.Reservation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserResponse {
    private Long userId;

    @Size(min = 3, max = 40, message = "Name must be between 3 and 40 characters")
    private String name;

    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
    private String email;

    private String phone;


    private String address;

    private String role;

    /** JWT token; set only on login response */
    private String token;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<Reservation> reservations;
}
