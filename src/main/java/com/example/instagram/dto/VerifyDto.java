package com.example.instagram.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VerifyDto {
    private final String memberId;
    private final String jwtToken;
}
