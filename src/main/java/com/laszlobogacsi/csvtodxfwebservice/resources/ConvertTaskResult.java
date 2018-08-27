package com.laszlobogacsi.csvtodxfwebservice.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

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
}
