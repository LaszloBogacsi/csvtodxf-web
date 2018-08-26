package com.laszlobogacsi.csvtodxfwebservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laszlobogacsi.csvtodxfwebservice.resources.JobResponse;

public class JsonMapper {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String fromObj(JobResponse obj) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }

}
