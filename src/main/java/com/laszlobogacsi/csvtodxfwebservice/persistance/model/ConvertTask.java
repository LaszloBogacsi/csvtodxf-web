package com.laszlobogacsi.csvtodxfwebservice.persistance.model;


import com.laszlobogacsi.csvtodxfwebservice.report.ConversionReport;
import com.laszlobogacsi.csvtodxfwebservice.resources.JobResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConvertTask {
    @Id
    @GeneratedValue()
    private long id;

    private String jobId;

    private String downloadId;

    private String downloadPath;

    @Enumerated(EnumType.STRING)
    private JobResult result;

    @Embedded
    private ConversionReport report;

}
