package com.inspire.inspirebe.attend.service;

import com.inspire.inspirebe.attend.entity.Attend;
import com.inspire.inspirebe.attend.repository.AttendRepository;
import com.inspire.inspirebe.user.dto.AdminAttendanceResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendServiceImpl implements AttendService {

    private final AttendRepository attendRepository;

    @Override
    public List<AdminAttendanceResponseDTO> getMonthlyAttendances() {

        LocalDate now = LocalDate.now();
        LocalDate start = now.withDayOfMonth(1);
        LocalDate end = now.withDayOfMonth(now.lengthOfMonth());

        List<Attend> attends =
                attendRepository.findByWorkDateBetween(start, end);

        // 🔥 직원별로 그룹화
        Map<Long, List<Attend>> grouped =
                attends.stream()
                        .collect(Collectors.groupingBy(
                                attend -> attend.getUser().getId()
                        ));

        return grouped.values().stream()
                .map(list -> {

                    Attend first = list.get(0);

                    int totalPay = list.stream()
                            .mapToInt(attend -> {

                                if (attend.getCheckIn() == null || attend.getCheckOut() == null)
                                    return 0;

                                long minutes = Duration.between(
                                        attend.getCheckIn(),
                                        attend.getCheckOut()
                                ).toMinutes();

                                return (int) minutes * first.getUser().getSalary() / 60;
                            })
                            .sum();

                    return AdminAttendanceResponseDTO.builder()
                            .name(first.getUser().getName())
                            .checkIn("-")
                            .checkOut("-")
                            .monthlyPay(totalPay)
                            .build();
                })
                .toList();
    }

    @Override
    public Integer getMonthlyTotalSalary() {

        return getMonthlyAttendances().stream()
                .mapToInt(AdminAttendanceResponseDTO::getMonthlyPay)
                .sum();
    }
}
