package com.example.instagram.dto;

import lombok.Data;

@Data
public class CommentUpdateDto {
    String memberId;
    String jwtToken;
    Long commentId;
    String contents;

}
