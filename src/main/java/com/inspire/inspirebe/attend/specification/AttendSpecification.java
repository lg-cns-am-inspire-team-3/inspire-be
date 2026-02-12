package com.inspire.inspirebe.attend.specification;

import com.inspire.inspirebe.attend.entity.Attend;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.YearMonth;

public class AttendSpecification {
    public static Specification<Attend> hasUserId(Long userId) {
        return (root, query, cb) -> userId == null ? null : cb.equal(root.get("user").get("id"), userId);
    }
    public static Specification<Attend> workDateBetween(Integer year, Integer month) {
        return (root, query, cb) -> {
            if(year == null || month == null) {
                return null;
            }
            YearMonth ym = YearMonth.of(year,month);
            LocalDate start = ym.atDay(1);
            LocalDate end = ym.atEndOfMonth();
            return cb.between(root.get("workDate"), start, end);
        };
    }
}
