package com.example.backend_lab.notification.kafka;

import com.example.backend_lab.notification.dto.NotificationSignal;
import com.example.backend_lab.notification.service.NotificationDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

// 카프카 토픽을 구독하고 있다가, 메시지가 오면
// 기존에 만들어둔 NotificationDispatcher를 그대로 호출한다.
// -> 이 클래스가 하는 일은 "카프카에서 받은 걸 dispatcher가 이해하는 형태로 넘겨주는 것"뿐
@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationDispatcher dispatcher;

    // topics: 구독할 토픽 이름 (Producer가 보낸 토픽과 동일해야 함)
    // groupId: 컨슈머 그룹 이름. 같은 그룹 안에서는 파티션을 나눠 가져가고,
    //          그룹이 다르면 같은 메시지를 각자 독립적으로 또 받는다.
    @KafkaListener(topics = "notification-events", groupId = "notification-group")
    public void consume(NotificationSignal signal) {
        log.info("[Kafka Consumer] 수신 → roomId={} messageId={}", signal.roomId(), signal.messageId());
        dispatcher.dispatch(signal, "KAFKA");
    }
}
