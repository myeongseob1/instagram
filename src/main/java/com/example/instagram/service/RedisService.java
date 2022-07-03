package com.example.instagram.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate redisTemplate;

    // 키-벨류 설정
    public void setValues(String memberId, String token){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(memberId, token, Duration.ofMinutes(40));  // 40분 뒤 메모리에서 삭제된다.

    }

    // 키값으로 벨류 가져오기
    public String getValues(String memberId){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(memberId);
    }

    // 키-벨류 삭제
    public void delValues(String token) {
        redisTemplate.delete(token.substring(7));
    }

}
