package com.tbi.webservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistributionBody {

    private Long distributionId;
    private String status;
}
