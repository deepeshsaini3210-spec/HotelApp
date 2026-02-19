package com.grandstay.hotel.util.wrappers;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserRegisterRequest {

    private String name;

    private String email;

    private String phone;
    private String password;
    private String address;

    private String role;

}
