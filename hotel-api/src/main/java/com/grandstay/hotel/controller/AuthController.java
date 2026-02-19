package com.grandstay.hotel.controller;

import org.springframework.cloud.openfeign.FeignClient;
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
    // Post /auth/login
    // Post /auth/register

    @SFApiResponses
    @PostMapping("/register")
    public UserResponse createUserAndUpdate(
        @RequestBody UserRegisterRequest User
    );

    @SFApiResponses
    @PostMapping("/login")
    public UserResponse userLogin (
        @RequestBody UserLoginRequest user
    );



}
