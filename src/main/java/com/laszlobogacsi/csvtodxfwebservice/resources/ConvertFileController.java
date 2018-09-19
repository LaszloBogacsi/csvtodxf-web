package com.laszlobogacsi.csvtodxfwebservice.resources;

import com.laszlobogacsi.csvtodxfwebservice.DrawingConfig;
import com.laszlobogacsi.csvtodxfwebservice.StorageService;
import com.laszlobogacsi.csvtodxfwebservice.convertjob.ConvertJob;
import com.laszlobogacsi.csvtodxfwebservice.convertjob.JobManager;
import com.laszlobogacsi.csvtodxfwebservice.persistance.model.ConvertTask;
import com.laszlobogacsi.csvtodxfwebservice.persistance.repository.ConvertTaskRepository;
import com.laszlobogacsi.csvtodxfwebservice.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;


@RestController
public class ConvertFileController {

    @Autowired
    @Qualifier("fileStorageService")
    private StorageService fileStorageService;

    @Autowired
    private JobManager manager;

    @Autowired
    private ConvertTaskRepository convertTaskRepository;

    @CrossOrigin(origins = "http://evil.com/")
    @RequestMapping("/convert")
    @PostMapping
    ResponseEntity convert(@RequestBody DrawingConfig config) {
        String convertJobId = UUID.randomUUID().toString();
        System.out.println("ConvertJob start");
        manager.start(new ConvertJob(convertJobId, config));
        System.out.println("return jobId");
        return ResponseEntity
                .status(202)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"response\":\"" + convertJobId + "\"}");
    }

    @CrossOrigin(origins = "http://evil.com/")
    @RequestMapping("/convert/status/{convertJobId}")
    @GetMapping
    ResponseEntity status(@PathVariable String convertJobId) {
        System.out.println("endpoint called");
        Optional<ConvertTask> optionalTask = convertTaskRepository.findByJobId(convertJobId);
        System.out.println("task found");
        ConvertTaskResult result;
        if (optionalTask.isPresent()) {
            ConvertTask task = optionalTask.get();
            result = ConvertTaskResult.builder()
                    .jobResult(task.getResult())
                    .downloadId(task.getDownloadId())
                    .durationInMillies(task.getReport().getDurationInMillies())
                    .fileSize(task.getReport().getFileSize())
                    .numberOfLinesConverted(task.getReport().getNumberOfLinesConverted())
                    .build();
        } else {
            result = ConvertTaskResult.builder().jobResult(JobResult.IN_PROGRESS).build();
        }

        return ResponseEntity
                .status(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body(JsonMapper.fromObj(result));
    }

    @RequestMapping("/download/{downloadId}") // this is should be the job id
    @GetMapping
    ResponseEntity download(@PathVariable String downloadId) {
        ConvertTask convertTask = convertTaskRepository.findByDownloadId(downloadId).get();
        Resource resource = fileStorageService.loadFileAsResource(convertTask.getDownloadPath());
        System.out.println("downloadId = " + downloadId);
        System.out.println("convertTask = " + convertTask.getDownloadPath());
        ResponseEntity response = ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
        return response;
    }

}
