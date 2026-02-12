package com.inspire.inspirebe.attend.specification;

import com.inspire.inspirebe.attend.entity.Attend;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.YearMonth;

public class AttendSpecification {
    public static Specification<Attend> hasUserId(Long userId) {
        return (root, query, cb) -> userId == null ? null : cb.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Attend> workDateBetween(Integer year, Integer month, Integer day) {
        return (root, query, cb) -> {
            if (year == null) {
                return null;
            }

            int m = month != null ? month : 1;
            int d = day != null ? day : 1;

            LocalDate start = LocalDate.of(year, m, d);
            LocalDate end;

            if (month != null && day != null) {
                // year+month+day 모두 지정 -> 단일 날짜
                end = start;
            } else if (month != null) {
                // year+month 지정 -> 해당 월 전체
                end = start.withDayOfMonth(start.lengthOfMonth());
            } else {
                // year만 지정 -> 해당 년도 전체
                start = LocalDate.of(year, 1, 1);
                end = LocalDate.of(year, 12, 31);
            }

            return cb.between(root.get("workDate"), start, end);
        };
    }
}
