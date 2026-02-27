package com.grandstay.hotel.controller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.grandstay.annotations.SFApiResponses;
import com.grandstay.hotel.util.wrappers.UserLoginRequest;
import com.grandstay.hotel.util.wrappers.UserRegisterRequest;
import com.grandstay.hotel.util.wrappers.UserResponse;

@FeignClient(
    name = "authController",
    path = "/auth",
    url = "${hotel-app-service.url}"
)
public interface AuthController {
    @PostMapping("/register")
    public UserResponse createUserAndUpdate(
        @RequestBody UserRegisterRequest User
    );

    @PostMapping("/login")
    public UserResponse userLogin (
        @RequestBody UserLoginRequest user
    );

    @GetMapping("/users/housekeeping")
    List<UserResponse> getHousekeepingUsers();
}
