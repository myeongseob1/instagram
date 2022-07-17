package com.example.instagram.dto;

import lombok.Data;

import java.util.List;


@Data
public class PostingFindDto {
    private Long postingId;
    private String nickName;
    private String title;
    private String contents;
    private String registerTime;
    private Long views;
    Long likeCnt;
    private List<CommentDto> commentList;
    private List<ImageDto> imageList;
}
