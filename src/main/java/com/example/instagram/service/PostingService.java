package com.example.instagram.service;

import com.example.instagram.dao.CommentDao;
import com.example.instagram.dao.PostingDao;
import com.example.instagram.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostingService {
    private final PostingDao postingDao;
    private final UserService userService;
    private final CommentDao commentDao;

    public String registerPosting(PostingRegisterDto postingRegisterDto){
        VerifyDto verifyDto = new VerifyDto(postingRegisterDto.getMemberId(),postingRegisterDto.getJwtToken());
        String secureResult = userService.verify(verifyDto);

        if(secureResult==null){
            log.info("token authorization fail");
            return "token authorization fail";
        }
        int registerResult = postingDao.insertPosting(postingRegisterDto.getMemberId(), postingRegisterDto.getTitle(), postingRegisterDto.getContents());
        if(registerResult <= 0){
            log.info("insert fail");
            return "insert fail";
        }

        return "success";
    }

    public List<PostingListDto> getPostingList(){
        return postingDao.selectPostingList();
    }

    public PostingFindDto getPostingById(Long postingId){
        PostingFindDto posting = postingDao.selectPostingById(postingId);
        List<CommentDto> commentList = commentDao.selectCommentList(postingId);
        posting.setCommentList(commentList);
        log.info("serivce:{}",posting);
        if(posting==null){
            log.info("posting not exist");
            return null;
        }
        return posting;
    }


    public String deletePosting(PostingDeleteDto postingDeleteDto) {
        VerifyDto verifyDto = new VerifyDto(postingDeleteDto.getMemberId(),postingDeleteDto.getJwtToken());
        String secureResult = userService.verify(verifyDto);

        if(secureResult==null){
            log.info("token authorization fail");
            return "token authorization fail";
        }

        int postingResult = postingDao.deletePosting(postingDeleteDto.getPostingId(),postingDeleteDto.getMemberId());
        if(postingResult<=0){
            log.info("delete fail");
            return "delete fail";
        }

        return "success";

    }
    public String modifyPosting(PostingUpdateDto postingUpdateDto){
        VerifyDto verifyDto = new VerifyDto(postingUpdateDto.getMemberId(),postingUpdateDto.getJwtToken());
        String secureResult = userService.verify(verifyDto);

        if(secureResult==null){
            log.info("token authorization fail");
            return "token authorization fail";
        }

        int postingResult = postingDao.updatePosting(postingUpdateDto.getPostingId(), postingUpdateDto.getMemberId(),postingUpdateDto.getTitle(),postingUpdateDto.getContents());
        if(postingResult <= 0){
            log.info("update fail");
            return "update fail";
        }
        return "success";
    }
}
