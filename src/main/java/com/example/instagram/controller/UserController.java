package com.example.instagram.controller;

import com.example.instagram.domain.Member;
import com.example.instagram.dto.TestDto;
import com.example.instagram.dto.UserDto;
import com.example.instagram.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String register(@RequestBody @Valid UserDto userDto){
        return null;
    }

    //consumes : content-type - 이 타입으로 쓰겠다 , produces: accept-type - 이 타입으로 보내주겠다
    //메소드 이름은 동사 (coding convention)
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(@RequestBody @Valid UserDto userDto){
        Member member = userService.login(userDto.getUserId(), userDto.getUserPw());

        if(member == null){
            return "login fail";
        }

        return "login success";
    }


}
