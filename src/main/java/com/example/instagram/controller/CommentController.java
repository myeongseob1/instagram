package com.example.instagram.controller;

import com.example.instagram.dto.*;
import com.example.instagram.exception.CommonErrorException;
import com.example.instagram.exception.ErrorCode;
import com.example.instagram.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@CrossOrigin("*")
@RequestMapping("/comment")
@RequiredArgsConstructor
@Api(value = "CommentController")
public class CommentController {

    private final CommentService commentService;


    @ApiOperation(value="댓글 등록",notes = "작성한 댓글을 등록하는 API")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String registerComment(@RequestBody @Valid CommentRegisterDto commentRegisterDto) {
        int registerComment = commentService.registerComment(commentRegisterDto);
        if(registerComment<=0){
            throw new CommonErrorException(ErrorCode.COMMENT_INSERT_ERROR);
        }
        return "success";
    }

    @ApiOperation(value="댓글 삭제",notes = "선택한 댓글 삭제")
    @PostMapping(value="/delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteComment(@RequestBody @Valid CommentDeleteDto commentDeleteDto){
        int deleteResult = commentService.deleteComment(commentDeleteDto);
        if(deleteResult<=0){
            throw new CommonErrorException(ErrorCode.COMMENT_DELETE_ERROR);
        }
        return "success";
    }
    @ApiOperation(value="댓글 수정",notes = "선택한 댓글 수정")
    @PostMapping(value="/modify",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String modifyComment(@RequestBody @Valid CommentUpdateDto commentUpdateDto){
        int modifyResult = commentService.modifyComment(commentUpdateDto);
        if(modifyResult<=0){
            throw new CommonErrorException(ErrorCode.COMMENT_UPDATE_ERROR);
        }
        return "success";
    }
}
