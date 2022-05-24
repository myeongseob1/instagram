package com.example.instagram.mapper;


import com.example.instagram.dto.TestDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {

    TestDto getTestInfo(String id);
    int registerTestMember(String id, String name);
}
