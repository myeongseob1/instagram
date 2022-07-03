package com.example.instagram.service;

import com.example.instagram.dao.CommentDao;
import com.example.instagram.dto.CommentRegisterDto;
import com.example.instagram.dto.VerifyDto;
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
        String secureResult = userService.verify(verifyDto);
        if(secureResult==null){
            log.info("token authorization fail");
            return "token authorization fail";
        }
        int registerResult = commentDao.insertComment(commentRegisterDto.getMemberId(),commentRegisterDto.getPostingId(),commentRegisterDto.getContents());
        if(registerResult <= 0){
            log.info("insert error");
            return null;
        }
        return "success";
    }

}
