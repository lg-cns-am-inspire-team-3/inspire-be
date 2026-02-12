package com.inspire.inspirebe.attend.service;

import com.inspire.inspirebe.attend.dto.AttendResponseDTO;
import com.inspire.inspirebe.attend.dto.AttendUpdateDTO;
import com.inspire.inspirebe.attend.entity.Attend;
import com.inspire.inspirebe.attend.mapper.AttendEntityMapper;
import com.inspire.inspirebe.attend.repository.AttendRepository;
import com.inspire.inspirebe.attend.specification.AttendSpecification;
import com.inspire.inspirebe.user.entity.UserEntity;
import com.inspire.inspirebe.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendServiceImpl implements AttendService {

    private static final String VALID_QR_TOKEN = "ATTENDANCE_QR_TEST_OKOK_LGCNS";

    private final AttendRepository attendRepository;
    private final UserService userService;


    @Override
    public AttendResponseDTO getAttend(Long id) {
        Attend attend = attendRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID: " + id + " 출결을 찾을 수 없습니다."));
        return AttendEntityMapper.toResponse(attend);
    }

    @Override
    public List<AttendResponseDTO> getAllAttends(Long userId, Integer year, Integer month) {

        Specification<Attend> spec = Specification
                .where(AttendSpecification.hasUserId(userId))
                .and(AttendSpecification.workDateBetween(year, month));

        List<Attend> results = attendRepository.findAll(
                spec,
                Sort.by(Sort.Direction.DESC, "workDate")
        );

        return results.stream()
                .filter(Attend::calculatable)
                .map(AttendEntityMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteAttend(Long id) {
        Attend attend = attendRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID: " + id + " 출결 정보를 찾을 수 없습니다."));
        attendRepository.delete(attend);
    }

    @Override
    public void updateAttend(Long id, AttendUpdateDTO attendUpdateDTO) {
        Attend attend = attendRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID: " + id + " 출결 정보를 찾을 수 없습니다."));

        attendUpdateDTO.getWorkDate().ifPresent(attend::changeWorkDate);
        attendUpdateDTO.getCheckIn().ifPresent(attend::changeCheckIn);
        attendUpdateDTO.getCheckOut().ifPresent(attend::changeCheckOut);
    }

    @Override
    public List<AttendResponseDTO> getMonthlyAttendances(YearMonth yearMonth) {

        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();

        List<Attend> attends = attendRepository.findAllByMonth(start, end);

        return attends.stream()
                .filter(Attend::calculatable)
                .map(AttendEntityMapper::toResponse)
                .toList();
    }


    @Override
    @Transactional
    public void attend(Long userId, String qrToken) { //출석처리 기능

        //security에서 설정한 후에는 if문 삭제, 로그인 안 하고 QR 접근 막는 코드
        if (userId == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        validateQrToken(qrToken);

        // 로그인한 사용자 정보 가져오기
        // 없는 사용자가 시도 불가 (인증이 되었다는 것은 로그인의 성공)
        UserEntity user = userService.getReferenceBy(userId);
        List<Attend> attends = attendRepository.findWithUserIdAndWorkDate(userId,LocalDate.now());

        if(attends.size() > 1) {
            throw new IllegalStateException("무언가 잘못됨");
        }

        if(attends.isEmpty()) {
            Attend attend = Attend.builder().
                    user(user).
                    build();
            attendRepository.save(attend);
            System.out.println("출근 처리 완료");
        } else {
            Attend attend = attends.get(0);

            if(attend.getCheckOut() != null) {
                throw new IllegalStateException("이미 퇴근 처리 되었습니다.");
            }
            attend.checkOut();
            System.out.println("퇴근 처리 완료");
        }
    }

    @Override
    public void validateQrToken(String qrToken) { // QR 토큰 확인하는 함수

        if (qrToken == null || qrToken.isBlank()) {
            throw new IllegalArgumentException("QR 토큰이 비어있습니다.");
        }

        if (!VALID_QR_TOKEN.equals(qrToken)) {
            throw new IllegalArgumentException("유효하지 않은 출석 QR입니다.");
        }
    }
}
