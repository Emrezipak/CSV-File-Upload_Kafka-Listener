package com.tbi.webservices.api;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tbi.webservices.dto.Webhook;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class KafkaController {

    @Value("${tbi.kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, Webhook> kafkaTemplate;

    @PostMapping
    public void sendMessage(@RequestBody Webhook webhook) {
        kafkaTemplate.send(topic, UUID.randomUUID().toString(), webhook);
    }
}
