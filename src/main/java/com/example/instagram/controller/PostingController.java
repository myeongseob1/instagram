package com.example.instagram.controller;

import com.example.instagram.domain.Posting;
import com.example.instagram.dto.*;
import com.example.instagram.service.PostingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Api(value = "PostingController")
public class PostingController {

    private final PostingService postingService;

    @ApiOperation(value="게시글 등록",notes = "작성한 게시글을 등록하는 API")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String registerPosting(@RequestBody @Valid PostingRegisterDto postingRegisterDto) {
        String registerPosting = postingService.registerPosting(postingRegisterDto);
        if(!registerPosting.equals("success")){
            //exception 처리를 하나 만들자
            return registerPosting;
        }
        return registerPosting;
    }
    @ApiOperation(value="게시글 조회",notes = "선택한 게시글 단건 조회")
    @GetMapping(value="", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PostingFindDto findPostingById(@RequestParam @Valid Long postingId) {
        PostingFindDto posting = postingService.getPostingById(postingId);
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

    @ApiOperation(value="게시글 삭제",notes = "선택한 게시글 삭제")
    @PostMapping(value="/delete",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deletePosting(@RequestBody @Valid PostingDeleteDto postingDeleteDto){
        String deleteResult = postingService.deletePosting(postingDeleteDto);
        if(!deleteResult.equals("success")){
            //exception 처리를 하나 만들자
            return deleteResult;
        }
        return deleteResult;

    }
    @ApiOperation(value="게시글 수정",notes = "선택한 게시글 수정")
    @PostMapping(value="/modify",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String modifyPosting(@RequestBody @Valid PostingUpdateDto postingUpdateDto){
        String modifyResult = postingService.modifyPosting(postingUpdateDto);
        if(!modifyResult.equals("success")){
            //exception 처리를 하나 만들자
            return modifyResult;
        }
        return modifyResult;
    }


}
