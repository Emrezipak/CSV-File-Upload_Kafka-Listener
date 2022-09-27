package com.tbi.webservices.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tbi.webservices.dto.Webhook;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.File;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage {

    private String message;
    private Integer status;
}
