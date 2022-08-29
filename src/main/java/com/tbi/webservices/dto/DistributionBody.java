package com.tbi.webservices.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistributionBody {

    private Long distributionId;
    private String status;
}
