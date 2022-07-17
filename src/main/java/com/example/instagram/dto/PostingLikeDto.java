package com.example.instagram.dto;

import lombok.Data;

@Data
public class PostingLikeDto {
    private String memberId;
    private String jwtToken;
    private Long postingId;
}
