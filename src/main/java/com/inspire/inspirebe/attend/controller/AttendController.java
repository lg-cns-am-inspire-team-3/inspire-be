package com.inspire.inspirebe.attend.controller;

import com.inspire.inspirebe.attend.dto.QrRequestDTO;
import com.inspire.inspirebe.attend.service.AttendService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/attend")
@RequiredArgsConstructor
public class AttendController {
    private final AttendService attendService;

    @PostMapping("/test")
    public ResponseEntity<Void> receiveQrToken(@RequestBody QrRequestDTO request) {
        System.out.println(">>>> 수신된 QR 토큰: " + request.getQrToken());
        return ResponseEntity.ok().build();
    }
}


