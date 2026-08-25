package com.example.backend_lab.notification.valkey;

import com.example.backend_lab.notification.dto.NotificationSignal;
import com.example.backend_lab.notification.service.NotificationDispatcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ValkeySubscriber implements MessageListener {

    private final NotificationDispatcher dispatcher; // 이건 스프링 빈이니 계속 생성자 주입

    // ObjectMapper는 스프링 빈이 아니라 그냥 직접 만들어서 쓴다
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            NotificationSignal signal = objectMapper.readValue(message.getBody(), NotificationSignal.class);
            log.info("[Valkey Subscriber] 수신 → roomId={} messageId={}", signal.roomId(), signal.messageId());
            dispatcher.dispatch(signal, "VALKEY");
        } catch (Exception e) {
            log.error("[Valkey Subscriber] 메시지 파싱 실패", e);
        }
    }
}