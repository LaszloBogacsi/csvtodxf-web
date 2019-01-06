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

import java.util.concurrent.*;

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

    public void start(ConvertJob job) {
            executorService.executeAsyncJob(job)
                    .thenAcceptAsync(response -> {
                        final FileCompressionInfo fileCompressionInfo = createFrom(response.getReport());
                        compress(fileCompressionInfo);
                        save(response, fileCompressionInfo);
            });
            System.out.println("job saved");
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
                .result(jobResponse.getResult())
                .downloadId(jobResponse.getDownloadId())
                .jobId(jobResponse.getJobId())
                .report(jobResponse.getReport())
                .downloadPath(fileCompressionInfo.getCompressedFilePath())
                .build());
    }
}
