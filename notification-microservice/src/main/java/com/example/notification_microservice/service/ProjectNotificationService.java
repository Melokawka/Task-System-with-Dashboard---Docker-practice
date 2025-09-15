package com.example.notification_microservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProjectNotificationService {
    private final RestTemplate restTemplate;

    public ProjectNotificationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RabbitListener(queues = "#{ProjectQueue.name}")
    public void listen(String message) {
        System.out.println(message);

        try {
            Long projectId = parseProjectId(message);
            String completion = parseCompletion(message);

            System.out.println(projectId);
            System.out.println(completion);

            updateProjectCompletion(projectId, completion);

        } catch (Exception e) {
            System.err.println("Failed to process message: " + message);
            e.printStackTrace();
        }
    }

    private void updateProjectCompletion(Long projectId, String completion) {
        String url = "http://PROJECT-MICROSERVICE/projects/" + projectId + "/completion";

        HttpEntity<Integer> request = new HttpEntity<>(Integer.parseInt(completion.replace("%", "")));
        restTemplate.put(url, request);

        System.out.println("Updated Project " + projectId + " completion to " + completion);
    }

    private Long parseProjectId(String message) {
        String prefix = "Project ID: ";
        int start = message.indexOf(prefix) + prefix.length();
        int end = message.indexOf('.', start);
        return Long.parseLong(message.substring(start, end).trim());
    }

    private String parseCompletion(String message) {
        String prefix = "Completion: ";
        int start = message.indexOf(prefix) + prefix.length();
        int end = message.indexOf('%', start) + 1; // include '%' symbol

        return message.substring(start, end).trim();
    }
}
