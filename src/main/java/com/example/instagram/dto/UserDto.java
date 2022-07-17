package com.example.instagram.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserDto {
    private String memberId;
    private String name;
    private String email;
    @NotBlank(message = "아이디를 입력하세요")
    private String userId;

    @NotBlank(message = "패스워드를 입력하세요")
    private String userPw;
    private String phone;
    private String nickname;
    private String privateKey;
    private String publicKey;
}
