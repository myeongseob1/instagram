package com.example.instagram.service;

import com.example.instagram.domain.Member;
import com.example.instagram.dto.UserDto;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


public class UserServiceTest extends IntegrationTest{

    @Autowired
    UserService userService;


    @Test
    public void 회원가입후로그인() throws Exception{
        //given
        UserDto userDto = new UserDto();
        userDto.setUserId("user1");
        userDto.setUserPw("123123");
        userDto.setPhone("01012341234");
        userDto.setEmail("abcd@gmail.com");
        //when
        // 회원가입을 한 아이디와 로그인을 한후 리턴받은 id 결과
        String registerId = userService.register(userDto);
        Member loginMember = userService.login(userDto);

        //then
        //id 값이 같은지 검증
        assertEquals(loginMember.getMemberId(),registerId);
        //userId값이 같은지 검증
        assertEquals(loginMember.getUserId(),userDto.getUserId());


    }
    @Test
    public void 중복확인() throws Exception{
        //given
        UserDto user1 = new UserDto();
        user1.setUserId("user1");
        user1.setUserPw("123123");


        //when
        String registerId = userService.register(user1);
        String validCheckResult = userService.idValidChk("user1");
        String validCheckResult2 = userService.idValidChk("user2");

        //then
        //중복확인 실패시 null값이 반환된다
        assertNull(validCheckResult);
        //중복화인 성공시 null말고 다른 값이 반환된다
        assertNotNull(validCheckResult2);
    }

}