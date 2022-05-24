package com.example.instagram.dao;

import com.example.instagram.domain.Member;
import com.example.instagram.mapper.UserMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    private final UserMapper userMapper;

    public UserDao(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Member getUser(String userId, String userPw){
        return userMapper.getUser(userId,userPw);
    }


}
