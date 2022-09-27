package com.tbi.webservices.config;

import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class HttpConfig {

    private final PropertiesConfig propertiesConfig;
    @Bean
    public OkHttpClient okHttpClient(){
       return new OkHttpClient.Builder()
                .connectTimeout(propertiesConfig.getTIMEOUT_MILLIS(), TimeUnit.MILLISECONDS)
                .build();
    }
}
