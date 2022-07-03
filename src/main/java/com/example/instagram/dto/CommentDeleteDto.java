package com.example.instagram.dto;

import lombok.Data;

@Data
public class CommentDeleteDto {
    String memberId;
    String jwtToken;
    Long commentId;

}
