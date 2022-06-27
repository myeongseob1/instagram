package com.example.instagram.dto;

import lombok.Data;

@Data
public class VerifyDto {
    private String memberId;
    private String jwtToken;
}
