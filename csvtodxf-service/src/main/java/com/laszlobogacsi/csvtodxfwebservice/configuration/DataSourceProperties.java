package com.laszlobogacsi.csvtodxfwebservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "datasource")
@Configuration
public class DataSourceProperties {
    public String name;

    public DataSourceProperties() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
