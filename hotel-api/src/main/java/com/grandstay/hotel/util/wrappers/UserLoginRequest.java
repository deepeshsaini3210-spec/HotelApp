package com.grandstay.hotel.util.wrappers;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserLoginRequest {
    private String email;
    private String password;
}
