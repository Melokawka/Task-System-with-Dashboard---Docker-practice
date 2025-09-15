package com.example.notification_microservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TaskNotificationService {
    @RabbitListener(queues = "#{TaskQueue.name}")
    public void listen(String message) {
        System.out.println(message);
    }

}
