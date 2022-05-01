package com.example.instagram.controller;


import com.example.instagram.dao.TestDao;
import com.example.instagram.dto.TestDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
public class MainController {

    private final TestDao testDao;

    public MainController(TestDao testDao) {
        this.testDao = testDao;
    }


    @GetMapping("/")
    public String mainPage(){
        return "Hello";
    }

    @GetMapping(value = "/test/info")
    public TestDto getTestInfo(@RequestParam String id){
        if(StringUtils.isEmpty(id)){
            throw new NullPointerException("id가 null값 입니다");
        }
        return testDao.getTestInfo(id);
    }

    @PostMapping(value = "/test/info", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String registerTestMember(@RequestBody @Valid TestDto testMember){
        TestDto existInfo = testDao.getTestInfo(testMember.getId());
        if(existInfo != null){
            throw new RuntimeException("중복된 값 입니다");
        }
        int result = testDao.registerTestMember(testMember.getId(),testMember.getName());
        if(result>0) {
            return "Success";
        }
        else{
            return "Error";
        }
    }

}
