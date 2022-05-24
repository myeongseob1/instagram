package com.example.instagram.domain;

import lombok.Data;

@Data
public class Member {
    private int memberId;
    private String name;
    private String email;
    private String userId;
    private String userPw;
    private String phone;
    private String nickname;
}
