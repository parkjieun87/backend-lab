package com.example.backend_lab.notification.dto;

public record NotificationSignal(
        String roomId,     // 어느 채팅방에서 발생한 신호인지
        String messageId,  // MongoDB에서 실제 메시지를 찾을 때 쓸 ID (지금은 값만 넘김)
        long timestamp     // 신호 발생 시각
) {}
