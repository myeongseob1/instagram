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

    public int insertUserSecure(String userId, String publicKey, String privateKey){
        return userMapper.insertMemberSecure(userId,publicKey,privateKey);
    }

    public int insertUser(String name, String email, String userId, String userPw, String phone, String nickname){
        return userMapper.insertMember(name,email,userId,userPw,phone,nickname);
    }


}
