package com.example.backend_lab.notification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.ChannelTopic;

// Kafka는 @KafkaListener 어노테이션 하나로 "구독 등록"이 자동으로 됐지만,
// Redis(Valkey) pub/sub은 "누가 어떤 채널을 들을지"를 직접 컨테이너에 등록해줘야 함.
// 이 설정 클래스가 그 등록 작업을 담당한다.
@Configuration
public class ValkeyConfig {

    public static final String CHANNEL_NAME = "notification-channel";

    // RedisConnectionFactory는 spring-boot-starter-data-redis가 자동으로 만들어주는 빈
    // (application.properties의 host/port 설정을 읽어서 연결 정보를 구성함)
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory connectionFactory,
            org.springframework.data.redis.connection.MessageListener subscriber
    ) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        // "notification-channel" 이라는 채널이 발행되면 subscriber(ValkeySubscriber)가 받도록 등록
        container.addMessageListener(subscriber, new ChannelTopic(CHANNEL_NAME));

        return container;
    }
}