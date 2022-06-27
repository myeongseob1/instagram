package com.example.instagram.domain;

import lombok.Data;

@Data
public class Posting {
    private Long postingId;
    private String title;
    private String memberId;
    private String contents;
    private Long imageId;
    private Long commentId;
    private Long views;
}
