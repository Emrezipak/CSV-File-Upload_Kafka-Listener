package com.project.webservices.service;


import com.project.webservices.payload.request.WebHookStatus;
import com.project.webservices.payload.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.project.webservices.dto.Webhook;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaListenerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaListenerService.class);
    @Value("${project.kafka.topic}")
    private String topic;
    private final KafkaTemplate<String, Webhook> kafkaTemplate;

    @KafkaListener(
            topics = "${project.kafka.topic}",
            groupId = "${project.kafka.group.id}"
    )
    public void listen(@Payload Webhook webhook) {
        LOGGER.info("Message received.. MessageID : {} Message: {} Date : {}",
        webhook.getId(),
        webhook.getTaskId(),
        webhook.getStatus());
    }

    public ResponseMessage sendMessage(Webhook webhook){
        if(checkWebHookStatus(webhook.getStatus())){
            kafkaTemplate.send(topic, UUID.randomUUID().toString(), webhook);
            return ResponseMessage.builder()
                    .message("Successfully sent message. {taskId : "+webhook.getTaskId()
                            +" status : "+webhook.getStatus()+"}").
                     status(200).build();
        }

        return ResponseMessage.builder().message("Status Not found").status(400).build();
    }

    public boolean checkWebHookStatus(String status){
        if(WebHookStatus.Open.name().equalsIgnoreCase(status) ||
           WebHookStatus.Canceled.name().equalsIgnoreCase(status) ||
           WebHookStatus.New.name().equalsIgnoreCase(status) ||
           WebHookStatus.Expired.name().equalsIgnoreCase(status) ||
           WebHookStatus.Closed.name().equalsIgnoreCase(status)){
            return true;
        }
        return false;
    }

}
