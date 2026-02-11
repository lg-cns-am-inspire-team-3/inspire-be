package com.inspire.inspirebe.attend.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.inspire.inspirebe.attend.entity.Attend;
import com.inspire.inspirebe.attend.repository.AttendRepository;
import com.inspire.inspirebe.user.entity.UserEntity;
import com.inspire.inspirebe.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendService {

    private static final String VALID_QR_TOKEN = "ATTENDANCE_QR_TEST_OKOK_LGCNS";

    private final AttendRepository attendRepository;
    private final UserRepository userRepository;

    @Transactional
    public void attend(Long userId, String qrToken) { //출석처리 기능
        
        //security에서 설정한 후에는 if문 삭제, 로그인 안 하고 QR 접근 막는 코드
        if (userId == null) { 
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        validateQrToken(qrToken);

        // 로그인한 사용자 정보(userId) 가져오기
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자"));

        LocalDate today = LocalDate.now();

        Optional<Attend> optionalAttend =
                attendRepository.findByUserAndWorkDate(user, today);

        if (optionalAttend.isEmpty()) {
            Attend attend = new Attend(user);
            attendRepository.save(attend);

            System.out.println("출근 처리 완료");

        } else {

            Attend attend = optionalAttend.get();

            if (attend.getCheckOut() != null) {
                throw new IllegalStateException("이미 퇴근까지 완료되었습니다.");
            }

            attend.updateCheckOut(LocalDateTime.now());

            System.out.println("퇴근 처리 완료");
        }

    }


    private void validateQrToken(String qrToken) { // QR 토큰 확인하는 함수

        if (qrToken == null || qrToken.isBlank()) {
            throw new IllegalArgumentException("QR 토큰이 비어있습니다.");
        }

        if (!VALID_QR_TOKEN.equals(qrToken)) {
            throw new IllegalArgumentException("유효하지 않은 출석 QR입니다.");
        }
    }

}
