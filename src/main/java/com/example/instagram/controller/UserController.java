package com.example.instagram.controller;

import com.example.instagram.domain.Member;
import com.example.instagram.dto.TestDto;
import com.example.instagram.dto.UserDto;
import com.example.instagram.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Slf4j
@Validated
@RestController
@CrossOrigin("*")
@RequestMapping("/user")
@Api(value = "User Controller")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value="회원가입",notes = "user의 정보를 입력하여 회원가입")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String register(@RequestBody @Valid UserDto userDto) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, BadPaddingException, InvalidKeyException {
        userService.register(userDto);
        return null;
    }

    //consumes : content-type - 이 타입으로 쓰겠다 , produces: accept-type - 이 타입으로 보내주겠다
    //메소드 이름은 동사 (coding convention)
    @ApiOperation(value="로그인",notes = "user의 id와 pw 인증을 통한 로그인 기능")
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(@RequestBody @Valid UserDto userDto){
        Member member = userService.login(userDto.getUserId(), userDto.getUserPw());

        if(member == null){
            return "login fail";
        }

        return "login success";
    }


}
