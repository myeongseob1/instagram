package com.example.instagram.mapper;

import com.example.instagram.domain.Member;
import com.example.instagram.domain.MemberSecure;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    Member getUser(String userId);
    MemberSecure getUserSecure(String userId);
    int insertMemberSecure(String memberId,String privateKey, String publicKey);
    int insertMember(String memberId,String name, String email, String userId, String userPw, String phone, String nickname);
    int updateLoginTime(String memberId);
}
