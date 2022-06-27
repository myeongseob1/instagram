package com.example.instagram.controller;

import com.example.instagram.dto.PostingRegisterDto;
import com.example.instagram.service.PostingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@CrossOrigin("*")
@RequestMapping("/posting")
@Api(value = "Posting Controller")
public class PostingController {

    private final PostingService postingService;

    public PostingController(PostingService postingService) {
        this.postingService = postingService;
    }

    @ApiOperation(value="게시글 등록",notes = "작성한 게시글을 등록하는 API")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String registerPosting(@RequestBody @Valid PostingRegisterDto postingRegisterDto) {
        String registerPosting = postingService.registerPosting(postingRegisterDto);
        if(!registerPosting.equals("success")){
            return registerPosting;
        }
        return registerPosting;
    }
}
