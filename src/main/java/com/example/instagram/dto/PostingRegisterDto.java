package com.example.instagram.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
public class PostingRegisterDto {
    private String memberId;
    private String jwtToken;
    @NotBlank(message = "제목을 입력하세요")
    private String title;
    @NotBlank(message = "내용을 입력하세요")
    private String contents;
    MultipartFile file;

}
