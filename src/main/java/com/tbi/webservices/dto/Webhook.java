package com.tbi.webservices.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"taskId", "status"})
public class Webhook {
    private String id = UUID.randomUUID().toString();
    private Long taskId;
    private String status;
}
