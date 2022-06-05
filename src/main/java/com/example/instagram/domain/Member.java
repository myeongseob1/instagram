package com.example.instagram.domain;

import lombok.Data;

@Data
public class Member {
    private String memberId;
    private String name;
    private String email;
    private String userId;
    private String userPw;
    private String phone;
    private String nickname;
    private String jwtToken;
}
