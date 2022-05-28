package com.example.instagram.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserDto {
    private String memberId;
    private String name;
    private String email;
    @NotBlank
    private String userId;
    @NotBlank
    private String userPw;
    private String phone;
    private String nickname;
    private String privateKey;
    private String publicKey;
}
