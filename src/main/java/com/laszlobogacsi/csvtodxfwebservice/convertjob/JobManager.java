package com.laszlobogacsi.csvtodxfwebservice.convertjob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobManager {

    private JobExecutorService executorService;

    @Autowired
    public JobManager(JobExecutorService executorService) {
        this.executorService = executorService;
    }

    // returns a job id.
    public String execute(ConvertJob job) {

        String jobId = executorService.addToQueue(job);
        executorService.executeJob();
        return jobId;
    }
}
