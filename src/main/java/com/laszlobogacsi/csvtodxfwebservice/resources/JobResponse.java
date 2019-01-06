package com.laszlobogacsi.csvtodxfwebservice.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.laszlobogacsi.csvtodxfwebservice.report.ConversionReport;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JobResponse {

    @JsonProperty("id")
    private String jobId;

    @JsonProperty("downloadId")
    private String downloadId;

    @JsonProperty("report")
    private ConversionReport report;

    @JsonProperty("result")
    private JobResult result;
}
