package com.henry.util;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Setter
public class QueueUtils {

    private static final Logger logger = LoggerFactory.getLogger(QueueUtils.class);
    private static RabbitTemplate rabbitTemplate;

    public static void sendQueue(String queueName, Object data) {
        try {
            rabbitTemplate.convertAndSend(queueName, data);
        } catch (Exception e) {
            logger.error(">>>>>>>>> SendQueue Error: {}", e.getMessage());
        }
    }
}
