package com.laszlobogacsi.csvtodxfwebservice.convertjob;

import com.laszlobogacsi.csvtodxfwebservice.converter.Converter;
import com.laszlobogacsi.csvtodxfwebservice.converter.CsvToDxfConverter;
import com.laszlobogacsi.csvtodxfwebservice.file.CsvFileReader;
import com.laszlobogacsi.csvtodxfwebservice.file.FileReader;
import com.laszlobogacsi.csvtodxfwebservice.file.PathProvider;
import com.laszlobogacsi.csvtodxfwebservice.report.ConversionReport;
import com.laszlobogacsi.csvtodxfwebservice.resources.JobResponse;
import com.laszlobogacsi.csvtodxfwebservice.resources.JobResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class JobExecutorService {

    private static final int MAX_THREADS = 4;
    private PathProvider pathProvider;
    private ExecutorService executorService;
    private final FileReader fileReader;


    @Autowired
    public JobExecutorService(PathProvider pathProvider) {
        this.pathProvider = pathProvider;
        this.executorService = Executors.newFixedThreadPool(MAX_THREADS);
        fileReader = new CsvFileReader();
    }

    Future<JobResponse> executeJob(ConvertJob job) {
        return executorService.submit(() -> execute(job));
    }

    private JobResponse execute(ConvertJob job) {
        Converter converter = new CsvToDxfConverter(fileReader, pathProvider);
        try {
            Thread.currentThread().sleep(5000);
            ConversionReport report = converter.convert(job.config);
            return new JobResponse(job.getJobId(), generateDownloadId(), report, JobResult.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace(); // TODO: exception handling strategy
            return new JobResponse(job.getJobId(), "0", ConversionReport.builder().build(), JobResult.CONVERSION_ERROR);
        }
    }

    private String generateDownloadId() {
        return UUID.randomUUID().toString();
    }
}
