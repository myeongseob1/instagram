package com.example.instagram.service;

import com.example.instagram.domain.Member;
import com.example.instagram.dto.PostingListDto;
import com.example.instagram.dto.PostingRegisterDto;
import com.example.instagram.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class PostingServiceTest {
    @Autowired PostingService postingService;
    @Autowired UserService userService;
    Member member;
    /*
        CRUD TEST
     */

    @Before
    public void settingTestData() throws NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        //회원 생성
        UserDto user1 = new UserDto();
        user1.setUserId("abcd");
        user1.setUserPw("123");
        String memberId = userService.register(user1);

        member = userService.login(user1);
        //글생성
        PostingRegisterDto postingRegisterDto = new PostingRegisterDto();
        postingRegisterDto.setMemberId(member.getMemberId());
        postingRegisterDto.setJwtToken(member.getJwtToken());
        postingRegisterDto.setTitle("안녕하세요");
        postingRegisterDto.setContents("안녕못해요");
        postingService.registerPosting(postingRegisterDto);

        PostingRegisterDto postingRegisterDto2 = new PostingRegisterDto();
        postingRegisterDto2.setMemberId(member.getMemberId());
        postingRegisterDto2.setJwtToken(member.getJwtToken());
        postingRegisterDto2.setTitle("안녕하세요1234");
        postingRegisterDto2.setContents("안녕못해요1234");
        postingService.registerPosting(postingRegisterDto2);
    }

    @Test
    public void 글조회(){
        //given
        //when


        //then
    }
    @Test
    public void 글수정() throws Exception{
        //given

        //when

        //then
    }
    @Test
    public void 글삭제() throws Exception{
        //given

        //when

        //then
    }
}