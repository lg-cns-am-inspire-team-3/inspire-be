package com.inspire.inspirebe.user.service;

import com.inspire.inspirebe.user.dto.UserSignupRequest;

public interface UserService {

    /**
     * 회원가입을 처리하는 메서드입니다.
     * 
     * @param request 프론트엔드에서 보낸 회원 정보(ID, PW, 이름 등)
     */
    void signup(UserSignupRequest request);

    /**
     * 아이디가 이미 존재하는지 확인하는 메서드입니다.
     * 
     * @param loginId 중복 확인할 아이디
     * @return 중복이면 true, 사용 가능하면 false
     */
    boolean isIdDuplicated(String loginId);
}