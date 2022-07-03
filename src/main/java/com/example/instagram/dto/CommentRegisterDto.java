package com.example.instagram.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentRegisterDto {
    private String memberId;
    private Long postingId;
    private String jwtToken;
    @NotBlank(message = "내용을 입력하세요")
    private String contents;

}
