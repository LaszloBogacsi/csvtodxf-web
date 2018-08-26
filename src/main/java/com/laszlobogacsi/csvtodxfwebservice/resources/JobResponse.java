package com.laszlobogacsi.csvtodxfwebservice.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JobResponse {

    @JsonProperty("id")
    private String jobId;

    @JsonProperty("result")
    private JobResult result;
}
