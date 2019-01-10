package com.laszlobogacsi.csvtodxfwebservice;


import com.laszlobogacsi.csvtodxfwebservice.configuration.DataSourceProperties;
import com.laszlobogacsi.csvtodxfwebservice.configuration.UploadProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public UploadProperties uploadProperties() {
        return new UploadProperties();
    }

    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }
}
