package com.example.backend_lab.notification.service;

import com.example.backend_lab.notification.dto.NotificationSignal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

// 이 클래스가 이번 실습의 핵심.
// "신호가 어디서 왔든(Kafka든 Valkey든 수동 테스트든) 결국 여기로 모인다"
// 나중에 Kafka Consumer, Valkey Subscriber를 만들어도
// 둘 다 이 dispatch() 메서드 하나만 호출하게 될 것 -> 로직 중복 없이 재사용
@Component
@RequiredArgsConstructor // final 필드를 생성자 주입해주는 lombok 어노테이션
@Slf4j // log.info(...) 사용 가능하게 해주는 lombok 어노테이션
public class NotificationDispatcher {

    // 서버에서 클라이언트로 웹소켓 메시지를 보낼 때 쓰는 스프링 제공 도구
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * @param signal 새 메시지 신호 (본문 없이 방ID, 메시지ID만)
     * @param source 이 신호가 어디서 왔는지 구분용 ("KAFKA", "VALKEY", "MANUAL-TEST" 등)
     *               -> 학습 단계에서 "지금 이게 카프카 경로로 왔는지 발키 경로로 왔는지" 눈으로 보기 위한 용도
     */
    public void dispatch(NotificationSignal signal, String source) {
        log.info("[{}] 신호 수신 → room={} messageId={}", source, signal.roomId(), signal.messageId());

        // "/topic/room/{roomId}" 를 구독 중인 모든 클라이언트에게 signal을 그대로 전송
        // convertAndSend: 객체를 JSON으로 변환해서 보내주는 역할까지 알아서 처리
        messagingTemplate.convertAndSend(
                "/topic/room/" + signal.roomId(),
                signal
        );
    }
}