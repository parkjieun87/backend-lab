package com.example.backend_lab.notification.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// 이 설정 클래스 하나로 "웹소켓 서버를 켠다"는 의미.
// @EnableWebSocketMessageBroker : STOMP 프로토콜 기반 메시징을 활성화
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 클라이언트가 웹소켓에 "처음 접속(handshake)"할 때 붙는 엔드포인트.
    // 예: ws://localhost:8081/ws (내부적으로 SockJS가 http->ws 업그레이드 처리)
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // 학습용이라 전체 허용 (운영에서는 도메인 제한 필요)
                .withSockJS(); // 브라우저가 웹소켓을 지원 안 할 때 폴백(fallback) 처리
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        // 서버 -> 클라이언트로 보낼 때는 "/topic/..." 으로 시작하는 주소를 씀
        // 클라이언트는 이 주소를 구독(subscribe)해서 메시지를 받는다
        registry.enableSimpleBroker("/topic");

        // 클라이언트 -> 서버로 보낼 때는 "/app/..." 으로 시작 (이번 실습에서는 안 씀, 나중을 위한 설정)
        registry.setApplicationDestinationPrefixes("/app");
    }

}
