package com.ecole.scolaire.entity;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}