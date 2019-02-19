package com.laszlobogacsi.csvtodxfwebservice.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
public class ConvertTaskResult {
    @JsonProperty
    private String downloadId;
    @JsonProperty
    private int numberOfLinesConverted;
    @JsonProperty
    private long durationInMillies;
    @JsonProperty
    private double fileSize;
    @JsonProperty
    @Getter
    private JobResult jobResult;
}
