package com.example.instagram.dto;

import lombok.Data;

@Data
public class PostingListDto {
    private Long postingId;
    private String nickName;
    private String title;
    private String registerTime;
    private Long views;

}
