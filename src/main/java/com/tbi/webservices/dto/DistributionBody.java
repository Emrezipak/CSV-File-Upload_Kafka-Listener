package com.tbi.webservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistributionBody implements Serializable {

    private Long distributionId;
    private String status;
}
