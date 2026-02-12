package com.inspire.inspirebe.attend.service;

import com.inspire.inspirebe.attend.dto.AttendRequestDTO;
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

    private final AttendRepository attendRepository;
    private final UserService userService;

    /*
     * 새로운 attend를 기록
     * 중복 처리 필요
     */
    @Override
    @Transactional
    public void checkIn(Long userId, AttendRequestDTO request) {
        UserEntity user = userService.getReferenceBy(userId);
        Attend attend = Attend.builder()
                .user(user)
                .build();
        attendRepository.save(attend);
    }

    /*
     * 기존의 attend 수정
     */
    @Override
    @Transactional
    public void checkOut(Long userId, AttendRequestDTO request) {
        /*
        Attend attend = attendRepository.findWithUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("ID: " + userId + " 근무자를 찾을 수 없습니다."));
        attend.checkOut();
         */
    }

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
}
