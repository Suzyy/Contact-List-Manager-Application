package com.example.clmp.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicProducerService {
    
    @org.springframework.beans.factory.annotation.Value("${topic.name.producer}")
    private String topicName;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String message) {
        log.info("Payload sent: {}", message);
        kafkaTemplate.send(topicName, message);
    }
}
