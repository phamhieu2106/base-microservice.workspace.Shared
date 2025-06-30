package com.base.service;

import com.base.base.BaseObjectLoggAble;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQService extends BaseObjectLoggAble {
    private final RabbitTemplate rabbitTemplate;

    public void sendQueue(String queueName, Object data) {
        try {
            rabbitTemplate.convertAndSend(queueName, data);
        } catch (Exception e) {
            logger.error(">>>>>>>>> SendQueue Error: {}", e.getMessage());
        }
    }
}
