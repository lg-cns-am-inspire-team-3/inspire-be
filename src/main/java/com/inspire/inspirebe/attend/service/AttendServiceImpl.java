package com.inspire.inspirebe.attend.service;

import com.inspire.inspirebe.attend.repository.AttendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendServiceImpl implements AttendService {
    private final AttendRepository attendRepository;
}
