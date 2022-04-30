package com.example.instagram;


import com.example.instagram.dto.TestDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {

    TestDto getInfo(int id);

}
