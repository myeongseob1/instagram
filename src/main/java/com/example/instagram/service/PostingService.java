package com.example.instagram.service;

import com.example.instagram.dao.PostingDao;
import com.example.instagram.dto.PostingFindDto;
import com.example.instagram.dto.PostingListDto;
import com.example.instagram.dto.PostingRegisterDto;
import com.example.instagram.dto.VerifyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PostingService {
    private final PostingDao postingDao;
    private final RedisService redisService;
    private final UserService userService;

    public PostingService(PostingDao postingDao, RedisService redisService, UserService userService) {
        this.postingDao = postingDao;
        this.redisService = redisService;
        this.userService = userService;
    }

    public String registerPosting(PostingRegisterDto postingRegisterDto){
        VerifyDto verifyDto = new VerifyDto();
        verifyDto.setMemberId(postingRegisterDto.getMemberId());
        verifyDto.setJwtToken(postingRegisterDto.getJwtToken());
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
        log.info("serivce:{}",posting);
        if(posting==null){
            log.info("posting not exist");
            return null;
        }

        return posting;
    }



}