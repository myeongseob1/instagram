package com.example.instagram.dao;


import com.example.instagram.dto.TestDto;
import com.example.instagram.TestMapper;
import org.springframework.stereotype.Repository;



@Repository
public class TestDao{

    private final TestMapper testMapper;

    public TestDao(TestMapper testMapper) {
        this.testMapper = testMapper;
    }


    public TestDto getInfo(int id){
        return testMapper.getInfo(id);
    }
}
