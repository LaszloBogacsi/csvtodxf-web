package com.laszlobogacsi.csvtodxfwebservice.convertjob;

import com.laszlobogacsi.csvtodxfwebservice.converter.Converter;
import com.laszlobogacsi.csvtodxfwebservice.converter.CsvToDxfConverter;
import com.laszlobogacsi.csvtodxfwebservice.file.CsvFileReader;
import com.laszlobogacsi.csvtodxfwebservice.file.FileReader;
import com.laszlobogacsi.csvtodxfwebservice.file.PathProvider;
import com.laszlobogacsi.csvtodxfwebservice.report.ConversionReport;
import com.laszlobogacsi.csvtodxfwebservice.resources.JobResponse;
import com.laszlobogacsi.csvtodxfwebservice.resources.JobResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class JobExecutorService {
    private final Logger logger = LoggerFactory.getLogger(JobExecutorService.class);
    private static final int MAX_THREADS = 4;
    private ExecutorService executorService;
    private final Converter converter;


    @Autowired
    public JobExecutorService(PathProvider pathProvider) {
        this.executorService = Executors.newFixedThreadPool(MAX_THREADS);
        converter = new CsvToDxfConverter(new CsvFileReader(), pathProvider);
    }

    CompletableFuture<JobResponse> executeAsyncJob(ConvertJob job) {
        return CompletableFuture.supplyAsync(() -> execute(job), executorService);
    }

    private JobResponse execute(ConvertJob job) {
        try {
            ConversionReport report = converter.convert(job.config);
            return new JobResponse(job.getJobId(), generateDownloadId(), report, JobResult.SUCCESS);
        } catch (Exception e) {
            logger.error("Error " + e.getMessage() + " jobId: " + job.getJobId());
            return new JobResponse(job.getJobId(), "0", ConversionReport.builder().build(), JobResult.CONVERSION_ERROR);
        }
    }

    private String generateDownloadId() {
        return UUID.randomUUID().toString();
    }
}
