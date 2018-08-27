package com.laszlobogacsi.csvtodxfwebservice.report;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConversionReport {
    private String savedFilePath;
    private int numberOfLinesConverted;
    private long durationInMillies;
    private double fileSize;
}
