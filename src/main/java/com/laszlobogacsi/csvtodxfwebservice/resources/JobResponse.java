package com.laszlobogacsi.csvtodxfwebservice.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JobResponse {

    @JsonProperty("id")
    private String jobId;

    public JobResponse(String jobId) {
        this.jobId = jobId;
    }
}
