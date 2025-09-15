package com.example.project_microservice.service;

import com.example.project_microservice.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class SenderService {

    private final RabbitTemplate rabbitTemplate;

    public SenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend("", RabbitMQConfig.PROJECT_QUEUE, message);
        System.out.println("📤 Sent message: " + message);
    }
}