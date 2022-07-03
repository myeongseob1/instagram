package com.example.instagram.controller;

import com.example.instagram.dto.CommentRegisterDto;
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
        String registerComment = commentService.registerComment(commentRegisterDto);
        if(!registerComment.equals("success")){
            //exception 처리를 하나 만들자
            return registerComment;
        }
        return registerComment;
    }


}
