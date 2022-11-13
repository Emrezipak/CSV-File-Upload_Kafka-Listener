package com.project.webservices.api;


import com.project.webservices.service.KafkaListenerService;
import com.project.webservices.payload.response.ResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.webservices.dto.Webhook;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/webhook")
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaListenerService kafkaListenerService;
    @PostMapping
    public ResponseEntity<ResponseMessage> sendMessage(@RequestBody Webhook webhook) {
        return ResponseEntity.ok(kafkaListenerService.sendMessage(webhook));
    }
}
