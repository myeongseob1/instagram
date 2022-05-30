package com.example.instagram.dao;

import com.example.instagram.domain.Member;
import com.example.instagram.domain.MemberSecure;
import com.example.instagram.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class UserDao {
    private final UserMapper userMapper;

    public UserDao(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    public Member getUser(String userId){
        return userMapper.getUser(userId);
    }
    public MemberSecure getUserSecure(String userId){
        return userMapper.getUserSecure(userId);
    }


    public int insertUser(String memberId,String name, String email, String userId, String userPw, String phone, String nickname){
        return userMapper.insertMember(memberId,name,email,userId,userPw,phone,nickname);
    }


    public int insertUserSecure(String memberId, String privateKey, String publicKey){
        return userMapper.insertMemberSecure(memberId,privateKey,publicKey);
    }

    public int updateLoginTime(String memberId){
        return userMapper.updateLoginTime(memberId);
    }


}
