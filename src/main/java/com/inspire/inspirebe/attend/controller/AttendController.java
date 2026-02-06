package com.inspire.inspirebe.attend.controller;

import com.inspire.inspirebe.attend.service.AttendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/attend")
@RequiredArgsConstructor
public class AttendController {
    private final AttendService attendService;
}
