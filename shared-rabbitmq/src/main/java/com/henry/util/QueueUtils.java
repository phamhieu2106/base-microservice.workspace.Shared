package com.henry.util;

import com.henry.service.RabbitMQService;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Setter
public class QueueUtils {

    private static final Logger logger = LoggerFactory.getLogger(QueueUtils.class);
    private static RabbitMQService rabbitMQService;

    public QueueUtils(RabbitMQService rabbitMQService) {
        QueueUtils.rabbitMQService = rabbitMQService;
    }

    public static void sendQueue(String queueName, Object data) {
        try {
            rabbitMQService.sendQueue(queueName, data);
        } catch (Exception e) {
            logger.error(">>>>>>>>> SendQueue Error: {}", e.getMessage());
        }
    }
}
