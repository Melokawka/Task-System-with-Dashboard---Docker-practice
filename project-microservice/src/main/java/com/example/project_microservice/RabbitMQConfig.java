package com.example.project_microservice;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String PROJECT_QUEUE = "ProjectQueue";

    @Bean
    public Queue projectQueue() {
        return new Queue(PROJECT_QUEUE, true); // false = not durable
    }
}
