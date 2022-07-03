package com.example.instagram.service;

import com.example.instagram.dao.CommentDao;
import com.example.instagram.dto.*;
import com.example.instagram.exception.CommonErrorException;
import com.example.instagram.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentDao commentDao;
    private final UserService userService;

    public String registerComment(CommentRegisterDto commentRegisterDto){
        VerifyDto verifyDto = new VerifyDto(commentRegisterDto.getMemberId(),commentRegisterDto.getJwtToken());
        userService.verify(verifyDto);
        int registerResult = commentDao.insertComment(commentRegisterDto.getMemberId(),commentRegisterDto.getPostingId(),commentRegisterDto.getContents());
        if(registerResult <= 0){
            throw new CommonErrorException(ErrorCode.COMMENT_INSERT_ERROR);
        }
        return "success";
    }
    public String deleteComment(CommentDeleteDto commentDeleteDto) {
        VerifyDto verifyDto = new VerifyDto(commentDeleteDto.getMemberId(),commentDeleteDto.getJwtToken());
        userService.verify(verifyDto);

        int commentResult = commentDao.deleteComment(commentDeleteDto.getCommentId());
        if(commentResult<=0){
            throw new CommonErrorException(ErrorCode.COMMENT_DELETE_ERROR);
        }
        return "success";

    }
    public String modifyComment(CommentUpdateDto commentUpdateDto){
        VerifyDto verifyDto = new VerifyDto(commentUpdateDto.getMemberId(),commentUpdateDto.getJwtToken());
        userService.verify(verifyDto);

        int commentResult = commentDao.updateComment(commentUpdateDto.getCommentId(),commentUpdateDto.getContents());
        if(commentResult <= 0){
            throw new CommonErrorException(ErrorCode.COMMENT_UPDATE_ERROR);
        }
        return "success";
    }




}
