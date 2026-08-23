package com.example.backend_lab.notification.controller;

import com.example.backend_lab.notification.dto.NotificationSignal;
import com.example.backend_lab.notification.kafka.NotificationProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

// 실제 서비스라면 "메시지 저장 로직" 안에서 저장 성공 후 이 발행을 호출하겠지만,
// 지금은 학습 목적이라 컨트롤러에서 바로 발행
@RestController
@RequestMapping("/api/notify")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationProducer producer;

    @PostMapping
    public ResponseEntity<Void> notify(@RequestBody NotificationRequest req) {
        NotificationSignal signal = new NotificationSignal(
                req.roomId(),
                req.messageId(),
                Instant.now().toEpochMilli()
        );
        producer.publish(signal);
        return ResponseEntity.accepted().build();
    }

    public record NotificationRequest(String roomId, String messageId) {}
}