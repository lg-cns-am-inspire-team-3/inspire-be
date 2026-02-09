package com.inspire.inspirebe.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OneTimeTokenServiceImpl implements OneTimeTokenService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String PREFIX = "ONETIME:";

    @Override
    public void storeOneTimeToken(String token, String OAuth2UserVo) {

    }

    @Override
    public String getOAuth2User(String token) {
        return "";
    }

    @Override
    public void deleteOneTimeToken(String token) {

    }

    @Override
    public boolean hasTokenWithKey(String token) {
        return false;
    }
}
