package com.itheima.publisher.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class mqConfig {
    @Autowired
    private final RabbitTemplate rabbitTemplate;

    @PostConstruct
    private void init() {
        rabbitTemplate.setReturnsCallback(
                returned ->
                {
                    log.error("监听到了return Callback");
                    log.error("交换机{}", returned.getExchange());
                    log.error("路由{}", returned.getRoutingKey());
                    log.error("message{}", returned.getMessage());
                    log.error("replaycode{}", returned.getReplyCode());
                    log.error("replaytext{}", returned.getReplyText());
                }

        );
    }
}
