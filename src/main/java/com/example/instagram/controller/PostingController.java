package com.example.instagram.controller;

import com.example.instagram.domain.Posting;
import com.example.instagram.dto.PostingFindDto;
import com.example.instagram.dto.PostingListDto;
import com.example.instagram.dto.PostingRegisterDto;
import com.example.instagram.service.PostingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    @ApiOperation(value="게시글 조회",notes = "선택한 게시글 단건 조회")
    @GetMapping(value="", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PostingFindDto findPostingById(@RequestParam @Valid Long postingId) {
        PostingFindDto posting = postingService.getPostingById(postingId);
        log.info("controller:{}",posting);
        if(posting==null){
            return null;
        }
        return posting;
    }
    @ApiOperation(value="게시글 전체목록 조회",notes = "전체 게시글 조회")
    @GetMapping(value="/list", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PostingListDto> findPostingList() {
        List<PostingListDto> postingList = postingService.getPostingList();
        return postingList;
    }
}
