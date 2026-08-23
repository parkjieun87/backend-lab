package com.example.backend_lab.notification.kafka;

import com.example.backend_lab.notification.dto.NotificationSignal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

// "신호를 카프카 토픽에 발행"만 담당. 웹소켓이나 dispatcher는 전혀 모른다.
// -> 프로듀서는 "누가 구독하는지" 신경 안 쓰는 게 핵심 (느슨한 결합)
@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {
    private static final String TOPIC = "notification-events";

    // Kafka로 메시지를 보낼 때 쓰는 스프링 제공 도구.
    // <String, NotificationSignal> = <메시지 key 타입, 메시지 value(본문) 타입>
    private final KafkaTemplate<String, NotificationSignal> kafkaTemplate;

    public void publish(NotificationSignal signal) {
        // key를 roomId로 지정하는 이유:
        // 카프카는 "같은 key는 항상 같은 파티션"으로 보내기 때문에,
        // 같은 방(room)에서 발생한 이벤트들의 처리 순서가 보장된다.
        kafkaTemplate.send(TOPIC, signal.roomId(), signal);
        log.info("[Kafka Producer] 발행 완료 → topic={} roomId={}", TOPIC, signal.roomId());
    }
}
