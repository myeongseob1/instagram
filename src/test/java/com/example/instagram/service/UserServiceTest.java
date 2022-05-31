package com.example.instagram.service;

import com.example.instagram.dto.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Autowired UserService userService;

    @Test
    public void 회원가입() throws Exception{
        //given
        UserDto userDto = new UserDto();
        userDto.setUserId("abcdef");
        userDto.setUserPw("123123");
        //when
        String memberId = userService.register(userDto);


        //then
        assertEquals(userService.login(userDto.getUserId(),userDto.getUserPw()).getMemberId(),memberId);
    }

}