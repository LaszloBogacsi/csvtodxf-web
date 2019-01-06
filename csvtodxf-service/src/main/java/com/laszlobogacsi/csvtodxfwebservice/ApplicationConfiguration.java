package com.laszlobogacsi.csvtodxfwebservice;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public UploadProperties uploadProperties() {
        return new UploadProperties();
    }
}
