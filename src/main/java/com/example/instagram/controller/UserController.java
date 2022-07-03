package com.example.instagram.controller;

import com.example.instagram.domain.Member;
import com.example.instagram.dto.UserDto;
import com.example.instagram.dto.VerifyDto;
import com.example.instagram.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Slf4j
@Validated
@RestController
@CrossOrigin("*")
@RequestMapping("/user")
@RequiredArgsConstructor
@Api(value = "UserController")
public class UserController {


    private final UserService userService;

    @ApiOperation(value="회원가입",notes = "사용자의 정보 입력후 회원가입")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String register(@RequestBody @Valid UserDto userDto) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        String result = userService.register(userDto);
        if(result == null){
            return "register fail";
        }
        return "register success";
    }

    @ApiOperation(value = "ID 중복확인",notes = "회원가입시 아이디 중복확인")
    @GetMapping(value="/idValidChk", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String idValidChk(@RequestParam @Valid String userId){
        String result = userService.idValidChk(userId);
        if(result==null){
            return "ID Valid Check Fail";
        }
        return "ID Valid Check Success";
    }
    //consumes : content-type - 이 타입으로 쓰겠다 , produces: accept-type - 이 타입으로 보내주겠다
    //메소드 이름은 동사 (coding convention)
    @ApiOperation(value="로그인",notes = "user의 id와 pw 인증을 통한 로그인 기능")
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Member login(@RequestBody @Valid UserDto userDto) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Member member = userService.login(userDto);

        if(member == null){
            return null;
        }
        return member;
    }
    @ApiOperation(value="사용자 검증",notes = "사용중인 User가 맞는지 jwtToken을 기반으로 검증하는 과정")
    @PostMapping(value = "/verify", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String verifyUser(@RequestBody @Valid VerifyDto verifyDto) {
        userService.verify(verifyDto);
        return "ID verify Check Success";
    }
}
