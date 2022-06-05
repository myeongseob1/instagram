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
//Test 환경에서 Transactional 은 commit이 되지 않게 해줌
@Transactional
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired UserService userService;

    @Test
    public void 회원가입후로그인() throws Exception{
        //given
        UserDto userDto = new UserDto();
        userDto.setUserId("user1");
        userDto.setUserPw("123123");

        //when
        // 회원가입을 한 아이디와 로그인을 한후 리턴받은 id 결과
        String registerId = userService.register(userDto);
        String loginId = userService.login(userDto).getMemberId();

        //then
        //두 결과 값이 같은지 검증
        assertEquals(loginId,registerId);
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
        assertNotNull(validCheckResult2);
    }

}