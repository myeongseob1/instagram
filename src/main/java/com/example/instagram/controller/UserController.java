package com.example.instagram.controller;

import com.example.instagram.domain.Member;
import com.example.instagram.dto.UserDto;
import com.example.instagram.dto.VerifyDto;
import com.example.instagram.exception.CommonErrorException;
import com.example.instagram.exception.ErrorCode;
import com.example.instagram.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
            throw new CommonErrorException(ErrorCode.USER_INSERT_ERROR);
        }
        return "success";
    }

    @ApiOperation(value = "ID 중복확인",notes = "회원가입시 아이디 중복확인")
    @GetMapping(value="/idValidChk")
    public String idValidChk(@ApiParam(value = "사용자의 ID") @RequestParam @Valid String userId){
        String result = userService.idValidChk(userId);
        if(result==null){
            throw new CommonErrorException(ErrorCode.USER_ID_DUPLICATE_ERROR);
        }
        return "success";
    }
    //consumes : content-type - 이 타입으로 쓰겠다 , produces: accept-type - 이 타입으로 보내주겠다
    //메소드 이름은 동사 (coding convention)
    @ApiOperation(value="로그인",notes = "user의 id와 pw 인증을 통한 로그인 기능")
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Member login(@RequestBody @Valid UserDto userDto) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Member member = userService.login(userDto);

        if(member == null){
            throw new CommonErrorException(ErrorCode.USER_LOGIN_ERROR);
        }
        return member;
    }
}
