package com.tbi.webservices.payload.response;

import com.tbi.webservices.dto.DistributionBody;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileResponse {

    private String distributionBody;
    private String message;
    private String fileName;
}
