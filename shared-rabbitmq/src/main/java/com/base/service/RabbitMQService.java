package com.base.service;

import com.base.BaseObjectLoggAble;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQService extends BaseObjectLoggAble {
    private final RabbitTemplate rabbitTemplate;

    public void sendQueue(String queueName, Object data) {
        rabbitTemplate.convertAndSend(queueName, data);
    }
}
