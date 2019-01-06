package com.laszlobogacsi.csvtodxfwebservice.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ConversionReport {
    private String savedFilePath;
    private int numberOfLinesConverted;
    private long durationInMillies;
    private double fileSize;
}
