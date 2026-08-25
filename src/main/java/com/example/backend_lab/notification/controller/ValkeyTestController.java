package com.example.backend_lab.notification.controller;

import com.example.backend_lab.notification.dto.NotificationSignal;
import com.example.backend_lab.notification.valkey.ValkeyPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

// Kafka 경로(NotificationController)와 별개로,
// Valkey 경로만 따로 테스트해볼 수 있게 만든 엔드포인트
@RestController
@RequestMapping("/api/notify/valkey")
@RequiredArgsConstructor
public class ValkeyTestController {

    private final ValkeyPublisher publisher;

    @PostMapping
    public ResponseEntity<Void> notify(@RequestBody NotificationRequest req) {
        NotificationSignal signal = new NotificationSignal(
                req.roomId(), req.messageId(), Instant.now().toEpochMilli()
        );
        publisher.publish(signal);
        return ResponseEntity.accepted().build();
    }

    public record NotificationRequest(String roomId, String messageId) {}
}