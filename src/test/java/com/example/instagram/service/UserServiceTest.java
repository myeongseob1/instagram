package com.example.instagram.service;

import com.example.instagram.domain.Member;
import com.example.instagram.dto.UserDto;
import com.example.instagram.exception.CommonErrorException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class UserServiceTest{

    @Autowired UserService userService;


    @Test
    public void 로그인() throws Exception{
        //given
        UserDto userDto = new UserDto();
        userDto.setUserId("user1");
        userDto.setUserPw("123123");
        userDto.setPhone("01012341234");
        userDto.setEmail("abcd@gmail.com");

        String registerId = userService.register(userDto);
        //when
        // 회원가입을 한 아이디와 로그인을 한후 리턴받은 id 결과
        UserDto userDto2 = new UserDto();
        userDto2.setUserId("user1");
        userDto2.setUserPw("123123");
        Member loginMember = userService.login(userDto2);

        //then
        //id 값이 같은지 검증
        assertEquals(loginMember.getMemberId(),registerId);
        //userId값이 같은지 검증
        assertEquals(loginMember.getUserId(),userDto.getUserId());
    }



    @Test(expected = CommonErrorException.class)
    public void 비밀번호오류() throws Exception{
        //given
        UserDto userDto2 = new UserDto();
        userDto2.setUserId("user1");
        userDto2.setUserPw("123123");

        UserDto userDto = new UserDto();
        userDto.setUserId("user1");
        userDto.setUserPw("123124");

        //when
        Member loginMember = userService.login(userDto);

        //then
        fail("공통처리 exception이 발생해야한다.");
    }
    
    @Test
    public void 중복확인() throws Exception{
        //given
        UserDto user1 = new UserDto();
        user1.setUserId("user1");
        user1.setUserPw("123123");


        //when
        String registerId = userService.register(user1);
        String validCheckResult2 = userService.idValidChk("user2");

        //then
        //중복화인 성공시 null말고 다른 값이 반환된다
        assertNotNull(validCheckResult2);
    }
    @Test(expected = CommonErrorException.class)
    public void 중복확인_예외() throws Exception{
        //given
        UserDto user1 = new UserDto();
        user1.setUserId("user1");
        user1.setUserPw("123123");
        String registerId = userService.register(user1);

        //when
        String validCheckResult = userService.idValidChk("user1");

        //then
        fail("공통처리 exception이 발생해야한다.");
    }
}