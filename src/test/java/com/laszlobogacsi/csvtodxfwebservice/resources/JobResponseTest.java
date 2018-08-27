package com.laszlobogacsi.csvtodxfwebservice.resources;

import com.laszlobogacsi.csvtodxfwebservice.report.ConversionReport;
import org.junit.Test;

public class JobResponseTest {

    @Test
    public void name() {
        JobResponse response = new JobResponse("123345", ConversionReport.builder().build(), JobResult.SUCCESS);
    }
}