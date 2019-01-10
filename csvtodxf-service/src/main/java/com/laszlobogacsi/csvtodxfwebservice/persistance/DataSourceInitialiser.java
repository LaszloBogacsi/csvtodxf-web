package com.laszlobogacsi.csvtodxfwebservice.persistance;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;


@Component
public class DataSourceInitialiser implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        EntityManager em; // initialize database somehow if it doesnt exist


    }
}
