package com.tbi.webservices.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.tbi.webservices.dto.Webhook;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaListenerService {

    @KafkaListener(
            topics = "${tbi.kafka.topic}",
            groupId = "${tbi.kafka.group.id}"
    )
    
    public void listen(@Payload Webhook webhook) {
        log.info("Message received.. MessageID : {} Message: {} Date : {}",
        webhook.getId(),
        webhook.getTaskId(),
        webhook.getStatus());
    }
}
