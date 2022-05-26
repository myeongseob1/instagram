package com.example.instagram.mapper;

import com.example.instagram.dao.UserDao;
import com.example.instagram.domain.Member;
import com.example.instagram.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    Member getUser(String userId, String userPw);
    int insertMemberSecure(String userId,String privateKey, String publicKey);
    int insertMember(String name, String email, String userId, String userPw, String phone, String nickname);
}
