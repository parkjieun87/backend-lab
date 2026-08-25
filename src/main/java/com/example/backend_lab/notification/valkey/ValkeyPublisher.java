package com.example.backend_lab.notification.valkey;

import com.example.backend_lab.notification.config.ValkeyConfig;
import com.example.backend_lab.notification.dto.NotificationSignal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class ValkeyPublisher {

    private final StringRedisTemplate redisTemplate; // 스프링 빈, 계속 생성자 주입

    // ObjectMapper는 스프링 빈이 아니므로 직접 생성
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void publish(NotificationSignal signal) {
        try {
            String json = objectMapper.writeValueAsString(signal);
            redisTemplate.convertAndSend(ValkeyConfig.CHANNEL_NAME, json);
            log.info("[Valkey Publisher] 발행 완료 → channel={} roomId={}", ValkeyConfig.CHANNEL_NAME, signal.roomId());
        } catch (Exception e) {
            log.error("[Valkey Publisher] 발행 실패", e);
        }
    }
}