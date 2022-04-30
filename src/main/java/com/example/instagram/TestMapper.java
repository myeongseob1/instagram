package com.example.instagram;


import com.example.instagram.Dto.TestDto;
import org.springframework.stereotype.Repository;

@Repository
public interface mapper {

    TestDto getInfo(int id);

}
