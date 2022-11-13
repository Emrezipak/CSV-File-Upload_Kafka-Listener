package com.project.webservices.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"taskId", "status"})
public class Webhook implements Serializable {
    private String id = UUID.randomUUID().toString();
    private Long taskId;
    private String status;
}
