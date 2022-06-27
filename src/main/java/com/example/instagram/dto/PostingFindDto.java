package com.example.instagram.dto;

import lombok.Data;


@Data
public class PostingFindDto {
    private Long postingId;
    private String nickName;
    private String title;
    private String contents;
    private String registerTime;
    private Long views;
}
