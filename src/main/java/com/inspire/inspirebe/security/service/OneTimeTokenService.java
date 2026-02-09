package com.inspire.inspirebe.security.service;

// 나중에 OAuth 로그인 구현에 쓰일 예정입니다.
public interface OneTimeTokenService {
    // one-time token 저장
    public void storeOneTimeToken(String token, String OAuth2UserVo);
    // one-time token 불러오기
    public String getOAuth2User(String token);
    // one-time token 삭제
    public void deleteOneTimeToken(String token);
    // one-time token 유뮤 확인
    public boolean hasTokenWithKey(String token);

    //    public void storeOneTimeToken(String token);
    //    public Long getUser(String token);
    //    public void deleteOneTimeToken(String token);
}
