package com.example.instagram.dto;

import lombok.Data;

@Data
public class PostingUpdateDto {
    String memberId;
    String jwtToken;
    Long postingId;
    String title;
    String contents;
}
