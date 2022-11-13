package com.project.webservices.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.webservices.dto.DistributionBody;
import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileResponse {

    private DistributionBody distributionBody;
    private String message;
    private String fileName;
    private HttpStatus httpStatus;
}
