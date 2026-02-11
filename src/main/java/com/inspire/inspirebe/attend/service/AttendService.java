package com.inspire.inspirebe.attend.service;

import org.springframework.stereotype.Service;

@Service
public interface AttendService {

    /**
     * 출석 요청 처리
     *
     * @param principal 인증된 사용자 정보 -> 이건 제가 아직 그 쪽 코드를 못 봐서 
     * @param qrToken 출석용 QR 토큰 -> String 타입으로 받아와서 QR토큰 검증할 때 사용하겠습니다.
     */

    // public void attend(Object principal, String qrToken) { //출석처리 기능

        // TODO 1. principal → 사용자 식별자 추출
        // TODO 2. QR 토큰 유효성 검증
        // TODO 3. attendanceRepository를 이용한 중복 출석 확인
        // TODO 4. Attendance 엔티티 생성 및 저장

        // 아직은 구현하지 않음
    // }

}
