package com.laszlobogacsi.csvtodxfwebservice.convertjob;

import com.laszlobogacsi.csvtodxfwebservice.converter.Converter;
import com.laszlobogacsi.csvtodxfwebservice.converter.CsvToDxfConverter;
import com.laszlobogacsi.csvtodxfwebservice.file.CsvFileReader;
import com.laszlobogacsi.csvtodxfwebservice.file.FileReader;
import com.laszlobogacsi.csvtodxfwebservice.file.PathProvider;
import com.laszlobogacsi.csvtodxfwebservice.report.ConversionReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class JobExecutorService {

    @Autowired
    @Qualifier("fileSystemPathProvider")
    private PathProvider pathProvider;

    Queue<ConvertJob> queue = new ConcurrentLinkedQueue<>();

    // TODO: create a threadpool executor service, so the jobs can execute in paralell
    // TODO: create JobResponse object <- this will be wrapped in the JobResult object

//    public JobResult execute() {
//        // public method to call form outside, maybe form the manager.
//    }

    void queueStatus() {
        // poll the queue to see if more jobs available
    }

    String addToQueue(ConvertJob job) {
        String jobId = job.getJobId();
        // add job to queue
        queue.add(job);
        return jobId;
    }

    ConvertJob pollJobQueue() {
        // get a job from the queue;
        return queue.poll();
    }

    void executeJob() {
        // execute a job from the queue
        execute(pollJobQueue());
    }

    private void execute(ConvertJob job) {
        // move these to the place pf execution
        FileReader fileReader = new CsvFileReader();
        ConversionReport conversionReport = new ConversionReport();
        Converter converter = new CsvToDxfConverter(fileReader, conversionReport, pathProvider);
        try {
            converter.convert(job.config);
        } catch (Exception e) {
            e.printStackTrace(); // TODO: exception handling strategy
        }
    }

}
