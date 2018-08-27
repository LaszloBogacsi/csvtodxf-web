package com.laszlobogacsi.csvtodxfwebservice.convertjob;

import com.laszlobogacsi.csvtodxfwebservice.file.FileCompressorService;
import com.laszlobogacsi.csvtodxfwebservice.persistance.model.ConvertTask;
import com.laszlobogacsi.csvtodxfwebservice.persistance.repository.ConvertTaskRepository;
import com.laszlobogacsi.csvtodxfwebservice.report.ConversionReport;
import com.laszlobogacsi.csvtodxfwebservice.resources.JobResponse;
import com.laszlobogacsi.csvtodxfwebservice.resources.JobResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class JobManager {

    private JobExecutorService executorService;
    private FileCompressorService compressorService;
    private ConvertTaskRepository convertTaskRepository;

    @Autowired
    public JobManager(JobExecutorService executorService, @Qualifier("zipFileCompressorService") FileCompressorService compressorService, ConvertTaskRepository convertTaskRepository) {
        this.executorService = executorService;
        this.compressorService = compressorService;
        this.convertTaskRepository = convertTaskRepository;
    }

    public JobResponse start(ConvertJob job) {
        try {
            final JobResponse jobResponse = executorService.executeJobInParallel(job).get(5, TimeUnit.SECONDS);
            final FileCompressionInfo fileCompressionInfo = createFrom(jobResponse.getReport());
            compress(fileCompressionInfo);
            save(jobResponse, fileCompressionInfo);
            return jobResponse;
        } catch (JobExecutionException | InterruptedException | ExecutionException | TimeoutException e) {
            return new JobResponse(job.getJobId(), "0", ConversionReport.builder().build(), JobResult.CONVERSION_ERROR);
        }

    }

    private void compress(FileCompressionInfo compressionInfo) {
        compressorService.compress(compressionInfo.getFileToCompressPath(), compressionInfo.getCompressedFilePath());
    }

    private FileCompressionInfo createFrom(ConversionReport report) {
        String fileToCompressPath = report.getSavedFilePath();
        return FileCompressionInfo.builder()
                .fileToCompressPath(fileToCompressPath)
                .compressedFilePath(fileToCompressPath.replaceAll("\\.dxf", ".zip"))
                .build();
    }

    private void save(JobResponse jobResponse, FileCompressionInfo fileCompressionInfo) {
        convertTaskRepository.save(ConvertTask.builder()
                .downloadId(jobResponse.getDownloadId())
                .jobId(jobResponse.getJobId())
                .downloadPath(fileCompressionInfo.getCompressedFilePath())
                .build());
    }
}
