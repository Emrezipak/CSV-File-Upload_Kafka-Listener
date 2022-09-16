package com.tbi.webservices.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "tbitaskoa")
public class PropertiesConfig {

    private String HEADER_URL;
    private String HEADER_TENANT;
    private String HEADER_SECRET_KEY;
    private Integer TIMEOUT_MILLIS;
}
