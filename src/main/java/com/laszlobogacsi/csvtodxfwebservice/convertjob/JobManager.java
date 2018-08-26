package com.laszlobogacsi.csvtodxfwebservice.convertjob;

import com.laszlobogacsi.csvtodxfwebservice.resources.JobResponse;
import com.laszlobogacsi.csvtodxfwebservice.resources.JobResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class JobManager {

    private JobExecutorService executorService;

    @Autowired
    public JobManager(JobExecutorService executorService) {
        this.executorService = executorService;
    }

    public JobResponse start(ConvertJob job) {

        String jobId = null;
        try {
            jobId = executorService.addToQueue(job);
            final JobResponse jobResponse = executorService.executeJobInParallel(job).get(5, TimeUnit.SECONDS);
            return jobResponse;
        } catch (JobExecutionException | InterruptedException | ExecutionException | TimeoutException e) {
            return new JobResponse(jobId, JobResult.CONVERSION_ERROR);
        }

    }
}
