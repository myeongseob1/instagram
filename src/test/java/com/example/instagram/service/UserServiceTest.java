package com.example.instagram.service;

import com.example.instagram.domain.Member;
import com.example.instagram.dto.UserDto;
import com.example.instagram.exception.CommonErrorException;
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
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class UserServiceTest{

    @Autowired UserService userService;
    UserDto userDto;
    String memberId;

    @Before
    public void settingTestData() throws NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        userDto = new UserDto();
        userDto.setUserId("user1");
        userDto.setUserPw("123123");
        userDto.setNickname("곽");

        memberId = userService.register(userDto);
    }

    @Test
    public void 로그인() throws Exception{
        //given
        UserDto user = new UserDto();
        user.setUserId("user1");
        user.setUserPw("123123");
        //when
        // 회원가입을 한 아이디와 로그인을 한후 리턴받은 id 결과
        Member loginMember = userService.login(user);

        //then
        //id 값이 같은지 검증
        assertEquals(loginMember.getMemberId(),memberId);
        //userId값이 같은지 검증
        assertEquals(loginMember.getUserId(),userDto.getUserId());
        //nickname값이 같은지 검증
        assertEquals(loginMember.getNickname(),userDto.getNickname());
    }

    @Test(expected = CommonErrorException.class)
    public void 아이디오류() throws Exception{
        //given
        UserDto user = new UserDto();
        user.setUserId("user2");
        user.setUserPw("123123");
        //세팅과 다른 비밀번호를 입력

        //when
        Member loginMember = userService.login(user);

        //then
        fail("공통처리 exception이 발생해야한다.");
        assertNull(loginMember);
    }


    @Test(expected = CommonErrorException.class)
    public void 비밀번호오류() throws Exception{
        //given
        UserDto user = new UserDto();
        user.setUserId("user1");
        user.setUserPw("123124");
        //세팅과 다른 비밀번호를 입력

        //when
        Member loginMember = userService.login(user);

        //then
        fail("공통처리 exception이 발생해야한다.");
        assertNull(loginMember);
    }
    
    @Test
    public void 중복확인(){
        //given
        String notDuplicateUserId = "user2";
        //when
        String validCheckResult2 = userService.idValidChk(notDuplicateUserId);

        //then
        assertEquals(validCheckResult2,"success");
    }

    @Test(expected = CommonErrorException.class)
    public void 중복확인_예외() throws Exception{
        //given
        String duplicatedUserId = "user1";
        //when
        String validCheckResult = userService.idValidChk(duplicatedUserId);

        //then
        fail("공통처리 exception이 발생해야한다.");
        assertNotEquals(validCheckResult,"success");
    }
}