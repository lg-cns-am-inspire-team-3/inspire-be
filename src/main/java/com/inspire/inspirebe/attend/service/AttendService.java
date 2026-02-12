package com.inspire.inspirebe.attend.service;

import com.inspire.inspirebe.attend.dto.AttendRequestDTO;
import com.inspire.inspirebe.attend.dto.AttendResponseDTO;
import com.inspire.inspirebe.attend.dto.AttendUpdateDTO;
import com.inspire.inspirebe.user.dto.AdminAttendanceResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface AttendService {

    /*
     * qr token과 함께 check in
     * 출/퇴근 구분은 어떻게?
     * 기준 마련해야함
     */
    AttendResponseDTO getAttend(Long id);

    List<AttendResponseDTO> getAllAttends(Long userId, Integer year, Integer month, Integer day);

    void deleteAttend(Long id);

    void updateAttend(Long id, AttendUpdateDTO attendUpdateDTO);

    List<AttendResponseDTO> getMonthlyAttendances(YearMonth yearMonth);

    @Transactional
    void attend(Long userId, String qrToken);

    void validateQrToken(String qrToken);


}
