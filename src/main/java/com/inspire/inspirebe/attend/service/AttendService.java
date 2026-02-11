package com.inspire.inspirebe.attend.service;

import com.inspire.inspirebe.user.dto.AdminAttendanceResponseDTO;
import java.util.List;

public interface AttendService {

    List<AdminAttendanceResponseDTO> getMonthlyAttendances();

    Integer getMonthlyTotalSalary();
}
