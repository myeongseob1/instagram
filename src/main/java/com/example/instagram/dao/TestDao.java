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


    public TestDto getTestInfo(String id){
        return testMapper.getTestInfo(id);
    }
    public int registerTestMember(String id, String name){
        return testMapper.registerTestMember(id,name);
    }

}
