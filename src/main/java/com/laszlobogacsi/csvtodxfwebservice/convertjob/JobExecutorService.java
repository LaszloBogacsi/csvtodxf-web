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

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class JobExecutorService {

    public static final int MAX_THREADS = 4;
    private final int MAX_QUEUE_SIZE = 100;
    private PathProvider pathProvider;
    private ExecutorService executorService;


    @Autowired
    public JobExecutorService(PathProvider pathProvider) {
        this.pathProvider = pathProvider;
        this.executorService = Executors.newFixedThreadPool(MAX_THREADS);
    }

    Queue<ConvertJob> queue = new ConcurrentLinkedQueue<>();

    // TODO: create JobResponse object <- this will be wrapped in the JobResult object

//    public JobResult execute() {
//        // public method to call form outside, maybe form the manager.
//    }

    int queueSize() {
        return queue.size();
    }

    String addToQueue(ConvertJob job) throws JobExecutionException{
        String jobId = job.getJobId();
        // add job to queue
        if (canAddToQueue()){
            queue.add(job);
        } else {
            throw new JobExecutionException("Queue is full, try adding new job later");
        }
        return jobId;
    }

    ConvertJob pollJobQueue() {
        return queue.poll();
    }

    Future<JobResponse> executeJobInParallel(ConvertJob job) throws JobExecutionException {
        addToQueue(job);
        return executorService.submit(() -> execute(pollJobQueue()));
    }



    private JobResponse execute(ConvertJob job) {
        // move these to the place pf execution
        FileReader fileReader = new CsvFileReader();
        ConversionReport conversionReport = new ConversionReport();
        Converter converter = new CsvToDxfConverter(fileReader, conversionReport, pathProvider);
        try {
            converter.convert(job.config);
            return new JobResponse(job.getJobId(), JobResult.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace(); // TODO: exception handling strategy
            return new JobResponse(job.getJobId(), JobResult.CONVERSION_ERROR);
        }
    }

    private boolean canAddToQueue() {
        return queueSize() <= MAX_QUEUE_SIZE;
    }

}
