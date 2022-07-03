package com.example.instagram.dto;

import lombok.Data;

@Data
public class PostingDeleteDto {
    String memberId;
    String jwtToken;
    Long postingId;
}
