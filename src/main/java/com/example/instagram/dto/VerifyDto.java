package com.example.instagram.dto;

import lombok.Data;

@Data
public class VerifyDto {
    private String memberId;
    private String jwtToken;

    public VerifyDto(String memberId, String jwtToken) {
        this.memberId = memberId;
        this.jwtToken = jwtToken;
    }
}
