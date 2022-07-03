package com.example.instagram.service;

import com.example.instagram.dao.CommentDao;
import com.example.instagram.dao.PostingDao;
import com.example.instagram.dto.*;
import com.example.instagram.exception.CommonErrorException;
import com.example.instagram.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostingService {
    private final PostingDao postingDao;
    private final UserService userService;
    private final CommentDao commentDao;

    public int registerPosting(PostingRegisterDto postingRegisterDto, MultipartFile file) throws IOException {
        VerifyDto verifyDto = new VerifyDto(postingRegisterDto.getMemberId(),postingRegisterDto.getJwtToken());
        userService.verify(verifyDto);
        if(!file.isEmpty()){
            String urlFile = "D:\\"+file.getOriginalFilename();
            uploadFile(file);
        }
        int registerResult = postingDao.insertPosting(postingRegisterDto.getMemberId(), postingRegisterDto.getTitle(), postingRegisterDto.getContents());
        if(registerResult <= 0){
            throw new CommonErrorException(ErrorCode.POSTING_INSERT_ERROR);
        }
        return registerResult;
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
            throw new CommonErrorException(ErrorCode.POSTING_SELECT_ERROR);
        }
        return posting;
    }


    public int deletePosting(PostingDeleteDto postingDeleteDto) {
        VerifyDto verifyDto = new VerifyDto(postingDeleteDto.getMemberId(),postingDeleteDto.getJwtToken());
        userService.verify(verifyDto);

        int postingResult = postingDao.deletePosting(postingDeleteDto.getPostingId());
        if(postingResult<=0){
            throw new CommonErrorException(ErrorCode.POSTING_DELETE_ERROR);
        }
        return postingResult;

    }
    public int modifyPosting(PostingUpdateDto postingUpdateDto){
        VerifyDto verifyDto = new VerifyDto(postingUpdateDto.getMemberId(),postingUpdateDto.getJwtToken());
        userService.verify(verifyDto);

        int postingResult = postingDao.updatePosting(postingUpdateDto.getPostingId(),postingUpdateDto.getTitle(),postingUpdateDto.getContents());
        if(postingResult <= 0){
            throw new CommonErrorException(ErrorCode.POSTING_UPDATE_ERROR);
        }
        return postingResult;
    }

    public void uploadFile(MultipartFile file) throws IOException {
        file.transferTo(new File("D:\\"+file.getOriginalFilename()));
    }
}