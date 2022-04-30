package com.example.instagram.controller;


import com.example.instagram.dao.TestDao;
import com.example.instagram.dto.TestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
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
    public TestDto getTestInfo(@RequestParam int id){
        log.info("aaa:{}",id);
        return testDao.getInfo(id);
    }

}
