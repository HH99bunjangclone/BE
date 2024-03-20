package com.sparta.hhztclone.domain.member.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class EmailRedisRepository {

    private final StringRedisTemplate redisTemplate;

    // 난수 저장
    public void saveCertificationNumber(String email, String certificationNumber) {
        redisTemplate.opsForValue()
                .set(email, certificationNumber, Duration.ofSeconds(180)); // 이메일 인증 유효 시간 3분
    }

    // 난수 가져오기
    public String getCertificationNumber(String email) {
        return redisTemplate.opsForValue().get(email);
    }

    // 난수 제거하기
    public void removeCertificationNumber(String email) {
        redisTemplate.delete(email);
    }

    // 키(이메일) 일치여부 확인
    public boolean hasKey(String email) {
        Boolean keyExists = redisTemplate.hasKey(email);
        return keyExists != null & keyExists;
    }
}
