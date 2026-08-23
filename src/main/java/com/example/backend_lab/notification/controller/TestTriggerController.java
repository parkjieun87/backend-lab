package com.example.backend_lab.notification.controller;

import com.example.backend_lab.notification.dto.NotificationSignal;
import com.example.backend_lab.notification.service.NotificationDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 지금 단계에서는 Kafka/Valkey가 아직 없으니
// curl로 직접 "신호가 왔다"고 흉내내기 위한 임시 컨트롤러.
// 2단계에서 Kafka Consumer를 만들면, 이 컨트롤러 대신
// Consumer가 dispatcher.dispatch(...)를 호출하게 됨 (이 클래스는 그때 지워도 됨)
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestTriggerController {

    private final NotificationDispatcher dispatcher;

    @PostMapping("/trigger")
    public ResponseEntity<Void> trigger(@RequestBody NotificationSignal signal) {
        dispatcher.dispatch(signal, "MANUAL-TEST");
        return ResponseEntity.ok().build();
    }
}
