package com.example.softwaresystemapi.dtos;

import com.example.softwaresystemapi.models.UserModel;

public class AuthResponseDto {
    private String token;
    private UserModel user;

    public AuthResponseDto(String token, UserModel user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
