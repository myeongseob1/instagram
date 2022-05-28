package com.example.instagram.domain;

import lombok.Data;

@Data
public class MemberSecure {
    private String memberId;
    private String privateKey;
    private String publicKey;

}
