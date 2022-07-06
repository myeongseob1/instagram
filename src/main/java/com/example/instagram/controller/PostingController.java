package com.example.instagram.controller;

import com.example.instagram.domain.Posting;
import com.example.instagram.dto.*;
import com.example.instagram.exception.CommonErrorException;
import com.example.instagram.exception.ErrorCode;
import com.example.instagram.service.PostingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
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
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String registerPosting(PostingRegisterDto postingRegisterDto) throws IOException {
        boolean registerResult = postingService.registerPosting(postingRegisterDto);
        if(!registerResult){
            throw new CommonErrorException(ErrorCode.POSTING_INSERT_ERROR);
        }
        return "success";
    }

    @ApiOperation(value="게시글 조회",notes = "선택한 게시글 단건 조회")
    @GetMapping(value="/{postingId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PostingFindDto findPostingById(@PathVariable @Valid Long postingId) {
        PostingFindDto posting = postingService.getPostingById(postingId);
        if(posting==null){
            throw new CommonErrorException(ErrorCode.POSTING_SELECT_ERROR);
        }
        return posting;
    }

    @ApiOperation(value="게시글 전체목록 조회",notes = "전체 게시글 조회")
    @GetMapping(value="/list", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PostingListDto> findPostingList() {
        return postingService.getPostingList();
    }

    @ApiOperation(value="게시글 삭제",notes = "선택한 게시글 삭제")
    @PostMapping(value="/delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deletePosting(@RequestBody @Valid PostingDeleteDto postingDeleteDto){
        int deleteResult = postingService.deletePosting(postingDeleteDto);
        if(deleteResult<=0){
            throw new CommonErrorException(ErrorCode.POSTING_DELETE_ERROR);
        }
        return "success";
    }
    @ApiOperation(value="게시글 수정",notes = "선택한 게시글 수정")
    @PostMapping(value="/modify",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String modifyPosting(@RequestBody @Valid PostingUpdateDto postingUpdateDto){
        int modifyResult = postingService.modifyPosting(postingUpdateDto);
        if(modifyResult<=0){
            throw new CommonErrorException(ErrorCode.POSTING_UPDATE_ERROR);
        }
        return "success";
    }

}
